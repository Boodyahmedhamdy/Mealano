package io.github.boodyahmedhamdy.mealano.plans.presenter;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;

import java.util.TimeZone;
import java.util.stream.Collectors;

import io.github.boodyahmedhamdy.mealano.datalayer.datasources.local.db.entities.PlanEntity;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.plans.PlansRepository;
import io.github.boodyahmedhamdy.mealano.datalayer.repos.users.UsersRepository;
import io.github.boodyahmedhamdy.mealano.plans.contract.PlansPresenter;
import io.github.boodyahmedhamdy.mealano.plans.contract.PlansView;
import io.github.boodyahmedhamdy.mealano.utils.network.NetworkMonitor;
import io.github.boodyahmedhamdy.mealano.utils.rx.OnBackgroundTransformer;
import io.reactivex.rxjava3.disposables.Disposable;

public class PlansPresenterImpl implements PlansPresenter {

    private static final String TAG = "PlansPresenter";
    PlansView view;
    PlansRepository plansRepository;
    UsersRepository usersRepository;
    NetworkMonitor networkMonitor;

    public PlansPresenterImpl(PlansView view, PlansRepository plansRepository, UsersRepository usersRepository, NetworkMonitor networkMonitor) {
        this.view = view;
        this.plansRepository = plansRepository;
        this.usersRepository = usersRepository;
        this.networkMonitor = networkMonitor;
    }

    @Override
    public void getAllPlans() {
        if(usersRepository.isLoggedIn()){
            view.setIsAuthenticated(true);

            Disposable dis = plansRepository.getAllPlansFromLocal(
                usersRepository.getCurrentUser().getUid()
            ).compose(new OnBackgroundTransformer<>())
                .subscribe(planEntities -> {
                    view.setPlans(planEntities);
                    Log.i(TAG, "getAllPlans: " + planEntities);
                }, throwable -> {
                    view.setErrorMessage(throwable.getLocalizedMessage());
                });
        } else {
            view.setIsAuthenticated(false);
        }
    }


    @Override
    public void getMealById(String mealId) {
        view.goToMealDetailsScreen(mealId);
    }

    @Override
    public void deletePlan(PlanEntity planEntity) {

        // delete from remote
        if(networkMonitor.isConnected()) {
            plansRepository.deletePlanFromRemote(planEntity)
                    .addOnSuccessListener(unused -> {
                        view.setSuccessMessage("Plan deleted successfully");
                        sync();
                    })
                    .addOnFailureListener(e -> {
                        view.setErrorMessage("Couldn't Delete the Plan");
                    });
        } else {
            view.setErrorMessage("Can't delete plan when you are offline");
        }

    }

    @Override
    public void sync() {
        if(usersRepository.isLoggedIn()) {
            Disposable dis = plansRepository.getAllPlansFromRemote(usersRepository.getCurrentUser().getUid())
                    .compose(new OnBackgroundTransformer<>())
                    .subscribe(remotePlanEntities -> {

                        Disposable dis2 = plansRepository.insertAllPlansToLocal(remotePlanEntities)
                                .compose(new OnBackgroundTransformer<>())
                                .subscribe(() -> {

                                    Disposable dis3 = plansRepository.deletePlansFromLocalNotIn(
                                            usersRepository.getCurrentUser().getUid(),
                                            remotePlanEntities.stream().map(planEntity -> planEntity.getMealId()).collect(Collectors.toList())
                                    ).compose(new OnBackgroundTransformer<>())
                                            .subscribe(() -> {
                                                view.setSuccessMessage("Sync Successfully");
                                            }, throwable -> { // error while deleting
                                                view.setErrorMessage("error while deleting");
                                            });

                                }, throwable -> {
                                    // error while inserting
                                    view.setErrorMessage("error while inserting");
                                });

                    }, throwable -> {
                        // error while getting from remote
                        view.setErrorMessage("error while getting from remote");
                    });

        }
    }

    @Override
    public void sharePlanToCalendar(PlanEntity planEntity, ContentResolver contentResolver) {

        Log.i(TAG, "sharePlanToCalendar: started");
        ContentValues values = new ContentValues();

        values.put(CalendarContract.Events.CALENDAR_ID, 1);
        values.put(CalendarContract.Events.TITLE, planEntity.getMealDTO().getStrMeal());
        values.put(CalendarContract.Events.DTSTART, planEntity.getDate());
        values.put(CalendarContract.Events.DTEND, planEntity.getDate() + 16*60*60*1000); // 16 hours
        values.put(CalendarContract.Events.ALL_DAY, true);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());

        try {
            Uri uri = contentResolver.insert(CalendarContract.Events.CONTENT_URI, values);
            long eventId = Long.parseLong(uri.getLastPathSegment());
            Log.i(TAG, "sharePlanToCalendar: added event with id: " + eventId);

            view.setSuccessMessage("added " + planEntity.getMealDTO().getStrMeal() + " Successfully to your Calendar");

        } catch (Exception e) {
            Log.e(TAG, "sharePlanToCalendar: ", e);
            view.setErrorMessage("Failed to Add " + planEntity.getMealDTO().getStrMeal() + " to your Calendar");
        }

    }
}

package edu.nutri.breast_feeding_101.example.interactor;

import android.view.View;
import edu.nutri.breast_feeding_101.example.presenter.Presenter;

/**
 * Created by ribads on 4/20/18.
 */

public class Interactor {

    Presenter presenter;
    public Interactor(View view){
        presenter = new Presenter(view);
    }

}

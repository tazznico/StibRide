package esi.atl.G57618.stibride;

import esi.atl.G57618.stibride.model.Model;
import esi.atl.G57618.stibride.presenter.Presenter;
import esi.atl.G57618.stibride.view.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Model model = new Model();
        View view = new View(stage);
        Presenter presenter = new Presenter(model, view);
        presenter.initialize();
        view.addPresenter(presenter);
        model.addObserver(presenter);
    }
}

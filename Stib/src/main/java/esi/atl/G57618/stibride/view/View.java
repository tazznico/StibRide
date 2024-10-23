package esi.atl.G57618.stibride.view;

import esi.atl.G57618.stibride.model.dto.FavoriteDto;
import esi.atl.G57618.stibride.model.dto.StationDto;
import esi.atl.G57618.stibride.presenter.Presenter;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class View {
    private final FxmlController ctrl;
    private FxmlControllerFavorite favoriteCtrl;
    private Stage favoriteStage;

    public View(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/stib.fxml"));
        loader.setController(new FxmlController());
        Pane root = loader.load();

        ctrl = loader.getController();
        Scene scene = new Scene(root);

        stage.setResizable(false);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("logo.png"))));
        stage.setTitle("HE2B ESI - Projet STIB");
        stage.setScene(scene);
        stage.show();

        makeFavoriteStage(stage);
    }

    private void makeFavoriteStage(Stage stage) throws IOException {
        favoriteStage = new Stage();
        favoriteStage.initModality(Modality.WINDOW_MODAL);
        favoriteStage.initOwner(stage);

        FXMLLoader loaderFavorite = new FXMLLoader(getClass().getResource("/favorite.fxml"));
        loaderFavorite.setController(new FxmlControllerFavorite());
        Pane rootFavorite = loaderFavorite.load();
        Scene sceneFavorite = new Scene(rootFavorite);

        favoriteCtrl = loaderFavorite.getController();
        favoriteStage.setResizable(false);
        favoriteStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("logo.png"))));
        favoriteStage.setTitle("HE2B ESI - Gestion favoris");
        favoriteStage.setScene(sceneFavorite);
    }

    public void showFavorite(StationDto source, StationDto destination, String name) throws IOException {
        favoriteStage.show();
        favoriteCtrl.setComboBox(source, destination);
        favoriteCtrl.setName(name);
    }

    public void addPresenter(Presenter presenter) {
        ctrl.setPresenter(presenter);
        favoriteCtrl.setPresenter(presenter);
    }

    public void initialize(ObservableSet<StationDto> allStations, ObservableSet<FavoriteDto> allFavorites) {
        ctrl.initialize(allStations, allFavorites);
        favoriteCtrl.initialize(allStations);
    }

    public void closeFavorite() {
        favoriteStage.close();
    }

    public void setMsgFavorite(String msg) {
        favoriteCtrl.setMsgFavorite(msg);
    }

    public void setMenuFavorite(ObservableSet<FavoriteDto> allFavorites) {
        ctrl.setMenuFavorite(allFavorites);
    }

    public void setStatusSearch(boolean isEnd) {
        ctrl.getStatusSearch().setText(isEnd ? "Recherche terminée" : "Une erreur est survenue, merci de réessayer.");
    }

    public void setChangeStation(List<String> listStation) {
        ctrl.setChangeStation(listStation);
    }

    public void setNbStation(int nb) {
        ctrl.getNbStation().setText("Nombre de station : " + nb);
    }

    public void setPathStations(ObservableList<StationDto> newList) {
        ctrl.setPathStations(newList);
    }
}

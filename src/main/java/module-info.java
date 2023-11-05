module com.github.jingerjesus.gameenginethreedee {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.github.jingerjesus.gameenginethreedee to javafx.fxml;
    //exports com.github.jingerjesus.gameenginethreedee;
    exports com.github.jingerjesus.gameenginethreedee.engine;
    opens com.github.jingerjesus.gameenginethreedee.engine to javafx.fxml;
}
module TechRV {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.base;
	requires javafx.graphics;
	requires java.prefs;
	
	opens application to javafx.graphics, javafx.fxml, javafx.base;
}

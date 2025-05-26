/**
 * 
 */
/**
 * 
 */
module PasswordManager {
	requires jbcrypt;
	requires com.h2database;
	requires mysql.connector.j;
	requires java.sql;
	requires jakarta.mail;
	requires jakarta.activation;
	requires java.net.http;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.controls;

	opens application to javafx.graphics, javafx.fxml;
	opens controller to javafx.fxml;
	
    exports application;
}
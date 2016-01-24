package Uni.Views;

import org.dobrivoje.auth.IAccessAuthControl;
import java.io.Serializable;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.superb.apps.utilities.Enums.LOGS;
import Main.MyUI;
import static Main.MyUI.DS;

/**
 * UI content when the user is not logged in yet.
 */
public class LoginScreen extends CssLayout {

    //<editor-fold defaultstate="collapsed" desc="Infrastructure">
    private static final String PASSWORD_HINT = "Enter a password for your account";
    private static final String DOMAIN_HINT = "Select the domain";
    
    public static final String[] DOMAINS = new String[]{"INTERMOL", "RIS", "LOCAL"};
    
    private TextField username;
    private PasswordField password;
    private ComboBox domain;
    
    private Button login;
    private Button forgotPassword;
    private final LoginListener loginListener;
    private final IAccessAuthControl accessControl;
    
    public LoginScreen(IAccessAuthControl accessControl, LoginListener loginListener) {
        this.loginListener = loginListener;
        this.accessControl = accessControl;
        buildUI();
        username.focus();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Layout creation">
    private void buildUI() {
        addStyleName("login-screen");
        
        // login form, centered in the available part of the screen
        Component loginForm = buildLoginForm();
        
        // layout to center login form when there is sufficient screen space
        // - see the theme for how this is made responsive for various screen
        // sizes
        VerticalLayout centeringLayout = new VerticalLayout();
        centeringLayout.setStyleName("centering-layout");
        centeringLayout.addComponent(loginForm);
        centeringLayout.setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
        
        // information text about logging in
        CssLayout loginInformation = buildLoginInformation();
        
        addComponent(centeringLayout);
        addComponent(loginInformation);
    }
    
    private Component buildLoginForm() {
        FormLayout loginForm = new FormLayout();
        
        loginForm.addStyleName("login-form");
        loginForm.setSizeUndefined();
        loginForm.setMargin(false);
        
        username = new TextField("Username");
        username.setIcon(FontAwesome.USER);
        username.setWidth(15, Sizeable.Unit.EM);
        loginForm.addComponent(username);
        
        password = new PasswordField("Password");
        password.setWidth(15, Sizeable.Unit.EM);
        password.setDescription(PASSWORD_HINT);
        password.setIcon(FontAwesome.LOCK);
        loginForm.addComponent(password);
        
        domain = new ComboBox("Domain", new ArrayList<>(Arrays.asList(DOMAINS)));
        domain.setWidth(15, Sizeable.Unit.EM);
        domain.setDescription(DOMAIN_HINT);
        domain.setIcon(FontAwesome.DASHBOARD);
        domain.setNullSelectionAllowed(false);
        domain.setTextInputAllowed(false);
        domain.setValue(DOMAINS[0]);
        loginForm.addComponent(domain);
        
        CssLayout buttons = new CssLayout();
        buttons.setStyleName("buttons");
        loginForm.addComponent(buttons);
        
        buttons.addComponent(login = new Button("Login"));
        login.setDisableOnClick(true);
        login.addClickListener((Button.ClickEvent event) -> {
            try {
                login();
            } finally {
                login.setEnabled(true);
            }
        });
        login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        
        buttons.addComponent(forgotPassword = new Button("Forgot password?"));
        forgotPassword.addClickListener((Button.ClickEvent event) -> {
            showNotification(new Notification(PASSWORD_HINT));
        });
        forgotPassword.addStyleName(ValoTheme.BUTTON_LINK);
        
        return loginForm;
    }
    
    private CssLayout buildLoginInformation() {
        CssLayout loginInformation = new CssLayout();
        loginInformation.setStyleName("login-information");
        Label loginInfoText = new Label(
                "<h1>MOL Serbia<br>SW Platform</br></h1>"
                        + "<h2>Wholesale App</h2>"
                        + "Please, use your existing</br>"
                        + "corporate Windows account,</br>"
                        + "to use the application.",
                ContentMode.HTML);
        loginInformation.addComponent(loginInfoText);
        return loginInformation;
    }
    
    private void showNotification(Notification notification) {
        notification.setDelayMsec(3000);
        notification.show(Page.getCurrent());
    }
    //</editor-fold>

    private void login() {
        String un;

        switch ((String) domain.getValue()) {
            case "INTERMOL":
                un = "INTERMOL\\";
                break;
            case "RIS":
                un = "YU.RIS.CORP\\";
                break;
            case "LOCAL":
            default:
                un = "";
                break;
        }

        try {
            un += username.getValue();
            accessControl.login(un, password.getValue());

            try {
                DS.getLOGController().addNew(
                        new Date(),
                        LOGS.LOGIN.toString(),
                        accessControl.getPrincipal() + " ,Domain=" + domain.getValue(),
                        MyUI.get().getLoggedISUser());

                loginListener.doAfterLogin();
            } catch (Exception ex) {
                Logger.getLogger(LoginScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception le) {
            try {
                DS.getLOGController().addNew(new Date(), LOGS.LOGIN_WRONG.toString(),
                        "Login attempt! Username=" + username.getValue()
                        + " Domain=" + domain.getValue(), null);
            } catch (Exception ex) {
                Logger.getLogger(LoginScreen.class.getName()).log(Level.SEVERE, null, ex);
            }

            showNotification(new Notification("Login failed",
                    "Please check your username and password and try again.",
                    Notification.Type.ERROR_MESSAGE));
            username.focus();
            password.setValue("");
        }
    }

    public interface LoginListener extends Serializable {

        void doAfterLogin();
    }
}

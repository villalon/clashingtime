/**
 * 
 */
package cl.uai.webcursos.client.horario;

import cl.uai.webcursos.client.event.AsyncCallFinishedEvent;
import cl.uai.webcursos.client.event.AsyncCallStartedEvent;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Jorge Villalon
 *
 */
public class Login extends AbstractAgendaComposite {

	private VerticalPanel vpanel;
	private VerticalPanel vpanelLoginForm;
	private VerticalPanel vpanelLogoutForm;
	private TextBox username;
	private PasswordTextBox password;
	private Button btnLogin;
	private Label lblError;
	private Label lblWelcome;
	private Button btnLogout;
	
	/**
	 * 
	 */
	public Login() {
		vpanel = new VerticalPanel();
		vpanelLoginForm = new VerticalPanel();
		vpanelLoginForm.setSpacing(1);
		vpanelLogoutForm = new VerticalPanel();
		vpanelLogoutForm.setVisible(false);
		vpanelLogoutForm.setSpacing(1);
		lblWelcome = new Label();
		lblError = new Label();
		lblError.addStyleName("errorMessage");
		lblError.setText("Correo UAI o contraseña incorrectos.");
		username = new TextBox();
		username.setStylePrimaryName("loginTextBox");
		username.setText("correo@uai.cl");
		password = new PasswordTextBox();
		password.setStylePrimaryName("loginTextBox");
		password.setText("Contraseña");
		
		btnLogout = new Button("Salir");
		btnLogout.setStylePrimaryName("loginButton");
		btnLogin = new Button("Ingresar");
		btnLogin.setStylePrimaryName("loginButton");
		
		final AsyncCallback<String> async = new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				DialogBox dbox = new DialogBox(true, true);
				dbox.setText("Error");
				dbox.setWidget(lblError);
				dbox.showRelativeTo(username);
				btnLogin.setEnabled(true);
				username.setEnabled(true);
				password.setEnabled(true);
				handlerManager.fireEvent(new AsyncCallFinishedEvent(true));
			}
			@Override
			public void onSuccess(String result) {
				lblWelcome.setText(result);
				vpanelLoginForm.setVisible(false);
				vpanelLogoutForm.setVisible(true);
				btnLogin.setEnabled(true);
				username.setEnabled(true);
				password.setEnabled(true);
				handlerManager.fireEvent(new AsyncCallFinishedEvent(false));
			}
		};
		
		btnLogin.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				btnLogin.setEnabled(false);
				username.setEnabled(false);
				password.setEnabled(false);
				handlerManager.fireEvent(new AsyncCallStartedEvent());
				getAgendaService().autenticaUsuario(username.getText(), password.getText(), async);
			}
		});
		
		btnLogout.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				btnLogout.setEnabled(false);
				getAgendaService().logoutUsuario(new AsyncCallback<String>() {
					
					@Override
					public void onSuccess(String result) {
						vpanelLoginForm.setVisible(true);
						vpanelLogoutForm.setVisible(false);
						RootPanel.get("menu").clear();
						RootPanel.get("menu").add(new MenuAgenda());
						RootPanel.get("main").setVisible(false);
						RootPanel.get("ayuda").setVisible(false);
						RootPanel.get("welcome").setVisible(true);
					}
					
					@Override
					public void onFailure(Throwable caught) {
					}
				});
			}
		});
		
		password.addKeyDownHandler(new KeyDownHandler() {
			
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					btnLogin.setEnabled(false);
					username.setEnabled(false);
					password.setEnabled(false);
					handlerManager.fireEvent(new AsyncCallStartedEvent());
					getAgendaService().autenticaUsuario(username.getText(), password.getText(), async);
				}
			}
		});
		
		vpanelLoginForm.add(username);
		vpanelLoginForm.add(password);
		vpanelLoginForm.add(btnLogin);
		vpanelLoginForm.setCellHorizontalAlignment(username, HasHorizontalAlignment.ALIGN_RIGHT);
		vpanelLoginForm.setCellHorizontalAlignment(password, HasHorizontalAlignment.ALIGN_RIGHT);
		vpanelLoginForm.setCellHorizontalAlignment(btnLogin, HasHorizontalAlignment.ALIGN_RIGHT);
		
		vpanelLogoutForm.add(lblWelcome);
		vpanelLogoutForm.add(btnLogout);
		vpanelLogoutForm.setCellHorizontalAlignment(lblWelcome, HasHorizontalAlignment.ALIGN_RIGHT);
		vpanelLogoutForm.setCellHorizontalAlignment(btnLogout, HasHorizontalAlignment.ALIGN_RIGHT);
		
		vpanel.add(vpanelLoginForm);
		vpanel.add(vpanelLogoutForm);

		initWidget(vpanel);
	}

}

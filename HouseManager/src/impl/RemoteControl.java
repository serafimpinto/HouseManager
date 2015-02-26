package impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import actuador.service.ActuadorService;
import environment.Environment;
import sensoralarm.service.SensorAlarmService;
import temphum.service.TempHumService;
import weatherstation.service.WeatherStationService;
import java.awt.Toolkit;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import leituras.service.LeiturasService;
import luminosidade.service.LuminosidadeService;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ButtonGroup;

public class RemoteControl extends JFrame {
	private static final long serialVersionUID = 8161221483222489317L;
	
	public BundleContext bc;
	private JPanel contentPane;
	private JToggleButton arCondicionado;
	private JToggleButton desumificador;
	private JToggleButton janela;
	private JToggleButton luz;
	private JButton nextTurn;
	
	private JLabel tempIn;
	private JLabel humIn;
	private JLabel lightIn;
	private JLabel tempOut;
	private JLabel humOut;
	private JLabel lightOut;
	private JLabel AC_intensity;
	private JLabel humIntensity;
	private JLabel lightIntensity;
	
	private JLabel alarmTemperatura;
	private JLabel alarmHumidade;
	private JLabel alarmLuz;
	private JLabel alarmJanela;
	
	private JButton btnNewButton;
	private JButton button;
	private JButton button_1;
	private JButton button_2;
	private JButton button_3;
	private JButton button_4;
	
	//Limites
	private JFrame limites;
	private JMenuItem definir;
	private JPanel newPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private ButtonGroup group;
	private JRadioButton aberta;
	private JRadioButton fechada;
	private JButton ok;
	private JButton reset;
	
	//Leituras
	private JFrame leituras;
	private JPanel pane;
	private JMenuItem consultar;
	private JLabel temp_in;
	private JLabel hum_in;
	private JLabel luz_in;
	private JLabel temp_out;
	private JLabel hum_out;
	private JLabel luz_out;
	private JLabel sucesso; 
	private JLabel warning;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RemoteControl frame = new RemoteControl();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public RemoteControl() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("icons/home.png"));
		initialize();
	}
	
	public RemoteControl(BundleContext context) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("HouseManager/icons/home.png"));
		this.bc = context;
		initialize();
	}
	
	private void preenche() {
		//Leituras
		ServiceReference<?> reference16 = bc.getServiceReference(LeiturasService.class.getName());
		while((reference16 = bc.getServiceReference(LeiturasService.class.getName())) == null) 
			System.out.println("***** Service "+LeiturasService.class.getName()+" is not yet ready...");
		LeiturasService service16 = (LeiturasService)bc.getService(reference16);
		
		//TempHum
		ServiceReference<?> reference = bc.getServiceReference(TempHumService.class.getName());
		while((reference = bc.getServiceReference(TempHumService.class.getName())) == null) 
			System.out.println("***** Service "+TempHumService.class.getName()+" is not yet ready...");
		TempHumService service = (TempHumService)bc.getService(reference);
		double tempInside = service.getTempIn(); tempIn.setText(String.format("%.2f", tempInside));
		double humInside = service.getHumIn(); humIn.setText(String.format("%.2f", humInside));
		service.pushTemp(tempInside);
		service.pushHum(humInside);
		
		//Luminosidade
		ServiceReference<?> reference2 = bc.getServiceReference(LuminosidadeService.class.getName());
		while((reference2 = bc.getServiceReference(LuminosidadeService.class.getName())) == null) 
			System.out.println("***** Service "+LuminosidadeService.class.getName()+" is not yet ready...");
		LuminosidadeService service2 = (LuminosidadeService)bc.getService(reference2);
		double lightInside = service2.getLumIn(); lightIn.setText(String.format("%.2f", lightInside));
		service2.push(lightInside);
		
		//WeatherStation
		ServiceReference<?> reference3 = bc.getServiceReference(WeatherStationService.class.getName());
		while((reference3 = bc.getServiceReference(WeatherStationService.class.getName())) == null) 
			System.out.println("***** Service "+WeatherStationService.class.getName()+" is not yet ready...");
		WeatherStationService service3 = (WeatherStationService)bc.getService(reference3);
		double tempOutside = service3.getTempOut(); tempOut.setText(String.format("%.2f", tempOutside));
		double humOutside = service3.getHumOut(); humOut.setText(String.format("%.2f", humOutside));
		double lightOutside = service3.getLumOut(); lightOut.setText(String.format("%.2f", lightOutside));
		service3.pushTemp(tempOutside);
		service3.pushHum(humOutside);
		service3.pushLight(lightOutside);

		//Actuador
		ServiceReference<?> reference4 = bc.getServiceReference(ActuadorService.class.getName());
		while((reference4 = bc.getServiceReference(ActuadorService.class.getName())) == null) 
			System.out.println("***** Service "+ActuadorService.class.getName()+" is not yet ready...");
		ActuadorService service4 = (ActuadorService)bc.getService(reference4);
	
		arCondicionado.setEnabled(true); desumificador.setEnabled(true); luz.setEnabled(true); janela.setEnabled(true);
		double i_ac = service4.getIntensityTemp(); AC_intensity.setText(String.valueOf(i_ac));
		double i_hum = service4.getIntensityHum(); humIntensity.setText(String.valueOf(i_hum));
		double i_light = service4.getIntensityLight(); lightIntensity.setText(String.valueOf(i_light));
		
		//SensorAlarm
		ServiceReference<?> reference14 = bc.getServiceReference(SensorAlarmService.class.getName());
		while((reference14 = bc.getServiceReference(SensorAlarmService.class.getName())) == null) 
			System.out.println("***** Service "+SensorAlarmService.class.getName()+" is not yet ready...");
		SensorAlarmService service14 = (SensorAlarmService)bc.getService(reference14);
		
		alarm(service14);
		limites(service14);
		leituras(service16,service,service2,service3);
	}
	
	public void limites(SensorAlarmService service14) {
		reset.setEnabled(true);
		ok.setEnabled(true);
		double tempMin = service14.getTempMin();
		textField.setText(String.valueOf(tempMin));
		double tempMax = service14.getTempMax();
		textField_1.setText(String.valueOf(tempMax));
		double humMin = service14.getHumMin();
		textField_2.setText(String.valueOf(humMin));
		double humMax = service14.getHumMax();
		textField_4.setText(String.valueOf(humMax));
		double lampMin = service14.getLampMin();
		textField_3.setText(String.valueOf(lampMin));
		double lampMax = service14.getLampMax();
		textField_5.setText(String.valueOf(lampMax));
		boolean w = service14.getWindow();
		if(w)
			group.setSelected(aberta.getModel(), true);
		else
			group.setSelected(fechada.getModel(), true);
	}
	
	public void leituras(LeiturasService l, TempHumService th, LuminosidadeService lu, WeatherStationService ws) {
		double tempin = l.getMedia(th.getLeiturasTemp()); temp_in.setText(String.format("%.2f",tempin));
		double humin = l.getMedia(th.getLeiturasHum()); hum_in.setText(String.format("%.2f", humin));
		double luzin = l.getMedia(lu.getLeituras()); luz_in.setText(String.format("%.2f", luzin));
		
		double tempout = l.getMedia(ws.getLeiturasTemp()); temp_out.setText(String.format("%.2f", tempout));
		double humout = l.getMedia(ws.getLeiturasHum()); hum_out.setText(String.format("%.2f", humout));
		double luzout = l.getMedia(ws.getLeiturasLight()); luz_out.setText(String.format("%.2f", luzout));
	}
	
	public void alarm(SensorAlarmService service13) {
		if(service13.isAlarmingTemp())
			alarmTemperatura.setVisible(true);
		else
			alarmTemperatura.setVisible(false);
		if(service13.isAlarmingHum())
			alarmHumidade.setVisible(true);
		else
			alarmHumidade.setVisible(false);
		if(service13.isAlarmingLamp())
			alarmLuz.setVisible(true);
		else
			alarmLuz.setVisible(false);
		if(service13.isAlarmingWindow())
			alarmJanela.setVisible(true);
		else
			alarmJanela.setVisible(false);
	}
	
	public void initializeLeituras() {
		leituras = new JFrame();
		leituras.setIconImage(Toolkit.getDefaultToolkit().getImage("HouseManager/icons/search.png"));
		leituras.setTitle("Leituras dos Sensores");
		leituras.setBounds(550, 100, 450, 300);
		pane = new JPanel();
		pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		pane.setLayout(null);
		
		JPanel panel2 = new JPanel();
		panel2.setBorder(new TitledBorder(null, "Valor M\u00E9dio dos Sensores", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel2.setBounds(10, 11, 415, 213);
		pane.add(panel2);
		panel2.setLayout(null);
		
		JLabel lblTemperatura = new JLabel("Temperatura (\u00BAC)");
		lblTemperatura.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTemperatura.setHorizontalAlignment(SwingConstants.CENTER);
		lblTemperatura.setBounds(20, 75, 115, 29);
		panel2.add(lblTemperatura);
		
		JLabel lblHumidade = new JLabel("Humidade (%)");
		lblHumidade.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblHumidade.setHorizontalAlignment(SwingConstants.CENTER);
		lblHumidade.setBounds(20, 125, 100, 29);
		panel2.add(lblHumidade);
		
		JLabel lblOutside = new JLabel("Outside");
		lblOutside.setHorizontalAlignment(SwingConstants.CENTER);
		lblOutside.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblOutside.setBounds(280, 34, 134, 29);
		panel2.add(lblOutside);
		
		JLabel lblInside = new JLabel("Inside");
		lblInside.setHorizontalAlignment(SwingConstants.CENTER);
		lblInside.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblInside.setBounds(136, 34, 134, 29);
		panel2.add(lblInside);
		
		JLabel lblMedia = new JLabel("Média");
		lblMedia.setForeground(SystemColor.textHighlight);
		lblMedia.setHorizontalAlignment(SwingConstants.CENTER);
		lblMedia.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMedia.setBounds(20, 34, 100, 29);
		panel2.add(lblMedia);
		
		JLabel lblLuz = new JLabel("Luz (lx)");
		lblLuz.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblLuz.setHorizontalAlignment(SwingConstants.CENTER);
		lblLuz.setBounds(20, 175, 100, 29);
		panel2.add(lblLuz);
		
		temp_in = new JLabel("-");
		temp_in.setForeground(SystemColor.textHighlight);
		temp_in.setHorizontalAlignment(SwingConstants.CENTER);
		temp_in.setFont(new Font("Tahoma", Font.BOLD, 16));
		temp_in.setBounds(136, 75, 134, 29);
		panel2.add(temp_in);
		
		temp_out = new JLabel("-");
		temp_out.setForeground(SystemColor.textHighlight);
		temp_out.setHorizontalAlignment(SwingConstants.CENTER);
		temp_out.setFont(new Font("Tahoma", Font.BOLD, 16));
		temp_out.setBounds(280, 75, 134, 29);
		panel2.add(temp_out);
		
		hum_in = new JLabel("-");
		hum_in.setForeground(SystemColor.textHighlight);
		hum_in.setHorizontalAlignment(SwingConstants.CENTER);
		hum_in.setFont(new Font("Tahoma", Font.BOLD, 16));
		hum_in.setBounds(136, 125, 134, 29);
		panel2.add(hum_in);
		
		luz_in = new JLabel("-");
		luz_in.setForeground(SystemColor.textHighlight);
		luz_in.setHorizontalAlignment(SwingConstants.CENTER);
		luz_in.setFont(new Font("Tahoma", Font.BOLD, 16));
		luz_in.setBounds(136, 175, 134, 29);
		panel2.add(luz_in);
		
		hum_out = new JLabel("-");
		hum_out.setForeground(SystemColor.textHighlight);
		hum_out.setHorizontalAlignment(SwingConstants.CENTER);
		hum_out.setFont(new Font("Tahoma", Font.BOLD, 16));
		hum_out.setBounds(280, 125, 134, 29);
		panel2.add(hum_out);
		
		luz_out = new JLabel("-");
		luz_out.setForeground(SystemColor.textHighlight);
		luz_out.setHorizontalAlignment(SwingConstants.CENTER);
		luz_out.setFont(new Font("Tahoma", Font.BOLD, 16));
		luz_out.setBounds(280, 175, 134, 29);
		panel2.add(luz_out);
		leituras.getContentPane().add(pane);
	}

	public void initializeLimites() {
		limites = new JFrame();
		limites.setTitle("Limites dos Sensores");
		limites.setBounds(100, 100, 450, 300);
		limites.setIconImage(Toolkit.getDefaultToolkit().getImage("HouseManager/icons/gear.png"));
		newPane = new JPanel();
		newPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		newPane.setLayout(null);
		
		JPanel panel2 = new JPanel();
		panel2.setBorder(new TitledBorder(null, "Limites", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel2.setBounds(10, 11, 415, 213);
		newPane.add(panel2);
		panel2.setLayout(null);
		
		JLabel lblTemperatura = new JLabel("Temperatura (\u00BAC)");
		lblTemperatura.setBounds(30, 30, 115, 16);
		lblTemperatura.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel2.add(lblTemperatura);
		
		JLabel lblHumidade = new JLabel("Humidade (%)");
		lblHumidade.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblHumidade.setBounds(30, 80, 105, 16);
		panel2.add(lblHumidade);
		
		JLabel lblLuz = new JLabel("Luz (lx)");
		lblLuz.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblLuz.setBounds(30, 130, 105, 16);
		panel2.add(lblLuz);
		
		textField = new JTextField(); 
		textField.setBounds(200, 29, 60, 20);
		panel2.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(340, 29, 60, 20);
		panel2.add(textField_1);
		
		JLabel min1 = new JLabel("Min.");
		min1.setBounds(175, 32, 30, 14);
		panel2.add(min1);
		
		JLabel max = new JLabel("M\u00E1x.");
		max.setBounds(312, 32, 30, 14);
		panel2.add(max);
		
		JLabel min = new JLabel("Min.");
		min.setBounds(175, 82, 30, 14);
		panel2.add(min);
		
		JLabel min2 = new JLabel("Min.");
		min2.setBounds(175, 132, 30, 14);
		panel2.add(min2);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(200, 79, 60, 20);
		panel2.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(200, 129, 60, 20);
		panel2.add(textField_3);
		
		JLabel max2 = new JLabel("M\u00E1x.");
		max2.setBounds(312, 82, 30, 14);
		panel2.add(max2);
		
		JLabel max3 = new JLabel("M\u00E1x.");
		max3.setBounds(312, 132, 30, 14);
		panel2.add(max3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(340, 79, 60, 20);
		panel2.add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(340, 129, 60, 20);
		panel2.add(textField_5);
		
		JLabel lblJanela = new JLabel("Janela");
		lblJanela.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblJanela.setBounds(30, 180, 105, 16);
		panel2.add(lblJanela);
		
		aberta = new JRadioButton("Aberta"); aberta.setFocusable(false);
		aberta.setBounds(175, 178, 85, 20);
		panel2.add(aberta);
		
		fechada = new JRadioButton("Fechada"); fechada.setFocusable(false);
		fechada.setBounds(315, 178, 85, 20);
		panel2.add(fechada);
		
		group = new ButtonGroup();
		group.add(aberta);
		group.add(fechada);
		
		Action submit = new AbstractAction("OK") {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			ServiceReference<?> reference15 = bc.getServiceReference(SensorAlarmService.class.getName());
			while((reference15 = bc.getServiceReference(SensorAlarmService.class.getName())) == null) 
				System.out.println("***** Service "+SensorAlarmService.class.getName()+" is not yet ready...");
			SensorAlarmService service15 = (SensorAlarmService)bc.getService(reference15);
			
			if((!textField.getText().equals("")) && (!textField_1.getText().equals("")) && (!textField_2.getText().equals("")) && 
					(!textField_3.getText().equals("")) && (!textField_4.getText().equals("")) && (!textField_5.getText().equals(""))) {
				if(aberta.isSelected()) 
					service15.setWindow(true);
				else
					service15.setWindow(false);
				
				try  
				  {  
					double tempMin = Double.parseDouble(textField.getText());
					service15.setTempMin(tempMin);
					
					double tempMax = Double.parseDouble(textField_1.getText());
					service15.setTempMax(tempMax);
						
					double humMin = Double.parseDouble(textField_2.getText());
					service15.setHumMin(humMin);
					double humMax = Double.parseDouble(textField_4.getText());
					service15.setHumMax(humMax);
						
					double lampMin = Double.parseDouble(textField_3.getText());
					service15.setLampMin(lampMin);
					double lampMax = Double.parseDouble(textField_5.getText());
					service15.setLampMax(lampMax);
				  }
				catch(NumberFormatException n) {
				}
				
					
				limites(service15);
				alarm(service15);
				warning.setVisible(false);
				sucesso.setVisible(true);
				Timer timer = new Timer(2000, new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				        sucesso.setVisible(false);
				    }
				});
				timer.setRepeats(false);
				timer.start();
			}
			else {
				sucesso.setVisible(false);
				warning.setVisible(true);
			}
			}
		};
		ok = new JButton(submit); ok.setEnabled(false);
		ok.setFocusable(false);
		ok.setBounds(20, 230, 89, 23);
		reset = new JButton("Limpar");
		reset.setFocusable(false);
		reset.setEnabled(false);
		reset.setBounds(119,230,89,23);
		reset.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e){
		        textField.setText("");
		        textField_1.setText("");
		        textField_2.setText("");
		        textField_3.setText("");
		        textField_4.setText("");
		        textField_5.setText("");
		    }
		});
		
		sucesso = new JLabel("Definições gravadas com sucesso"); sucesso.setVisible(false);
		sucesso.setBounds(218, 235, 280, 14);
		warning = new JLabel("Preencha todos os campos"); warning.setVisible(false);
		warning.setBounds(218, 235, 280, 14);
		
		newPane.add(sucesso);
		newPane.add(warning);
		newPane.add(reset);
		newPane.add(ok);
		limites.getContentPane().add(newPane);
	}
	
	/**
	 * Create the main frame.
	 */
	public void initialize() {
		setTitle("House Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 815, 450);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnDefinies = new JMenu("Op\u00E7\u00F5es");
		menuBar.add(mnDefinies);
		
		initializeLimites();
		definir = new JMenuItem("Definir Limites dos Sensores"); 
		definir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ServiceReference<?> reference15 = bc.getServiceReference(SensorAlarmService.class.getName());
				while((reference15 = bc.getServiceReference(SensorAlarmService.class.getName())) == null) 
					System.out.println("***** Service "+SensorAlarmService.class.getName()+" is not yet ready...");
				SensorAlarmService service15 = (SensorAlarmService)bc.getService(reference15);
				limites(service15);
				limites.getRootPane().setDefaultButton(ok);
				limites.setVisible(true);
				sucesso.setVisible(false);
				warning.setVisible(false);
			}
		});
		definir.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.Event.ALT_MASK));
		mnDefinies.add(definir);
		
		initializeLeituras();
		consultar = new JMenuItem("Consultar Leituras dos Sensores");
		consultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				leituras.setVisible(true);
			}
		});
		consultar.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.Event.ALT_MASK));
		mnDefinies.add(consultar
				);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Controlo", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 11, 370, 180);
		contentPane.add(panel);
		
		JLabel lblNewLabel = new JLabel("Ar Condicionado");
		lblNewLabel.setBounds(10, 23, 134, 29);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		btnNewButton = new JButton("-"); btnNewButton.setEnabled(false);
		btnNewButton.setBounds(217, 23, 41, 29); btnNewButton.setFocusable(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ServiceReference<?> reference5 = bc.getServiceReference(ActuadorService.class.getName());
				while((reference5 = bc.getServiceReference(ActuadorService.class.getName())) == null) 
					System.out.println("***** Service "+ActuadorService.class.getName()+" is not yet ready...");
				ActuadorService service5 = (ActuadorService)bc.getService(reference5);
				service5.decreaseAC();
				AC_intensity.setText(String.valueOf(service5.getIntensityTemp()));
			}
		});
		
		button = new JButton("+"); button.setEnabled(false);
		button.setBounds(268, 23, 41, 29);button.setFocusable(false);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ServiceReference<?> reference5 = bc.getServiceReference(ActuadorService.class.getName());
				while((reference5 = bc.getServiceReference(ActuadorService.class.getName())) == null) 
					System.out.println("***** Service "+ActuadorService.class.getName()+" is not yet ready...");
				ActuadorService service5 = (ActuadorService)bc.getService(reference5);
				service5.increaseAC();
				AC_intensity.setText(String.valueOf(service5.getIntensityTemp()));	
			}
		});
		
		arCondicionado = new JToggleButton("OFF"); arCondicionado.setEnabled(false);
		arCondicionado.setBounds(142, 23, 65, 29);arCondicionado.setFocusable(false);
		arCondicionado.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) 
			{
				ServiceReference<?> reference10 = bc.getServiceReference(ActuadorService.class.getName());
				while((reference10 = bc.getServiceReference(ActuadorService.class.getName())) == null) 
					System.out.println("***** Service "+ActuadorService.class.getName()+" is not yet ready...");
				ActuadorService service10 = (ActuadorService)bc.getService(reference10);	
				if (arCondicionado.isSelected()) {
					arCondicionado.setText("ON"); 
					btnNewButton.setEnabled(true);
					button.setEnabled(true);
					service10.setACOn(true);
					
				}
				else {
					arCondicionado.setText("OFF");
					btnNewButton.setEnabled(false);
					button.setEnabled(false);
					service10.setACOn(false);
				}
			}
		});
		
		JLabel lblWindow = new JLabel("Luz");
		lblWindow.setBounds(10, 103, 134, 29);
		lblWindow.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JLabel lblLuz = new JLabel("Janela");
		lblLuz.setBounds(10, 143, 134, 29);
		lblLuz.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		janela = new JToggleButton("Fechada"); janela.setEnabled(false);
		janela.setBounds(142, 144, 116, 29);janela.setFocusable(false);
		janela.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) 
			{
				ServiceReference<?> reference6 = bc.getServiceReference(ActuadorService.class.getName());
				while((reference6 = bc.getServiceReference(ActuadorService.class.getName())) == null) 
					System.out.println("***** Service "+ActuadorService.class.getName()+" is not yet ready...");
				ActuadorService service6 = (ActuadorService)bc.getService(reference6);
				if (janela.isSelected()) {
					service6.setWindow(true);
					janela.setText("Aberta");
				}
				else {
					service6.setWindow(false);
					janela.setText("Fechada");
				}
				ServiceReference<?> reference14 = bc.getServiceReference(SensorAlarmService.class.getName());
				while((reference14 = bc.getServiceReference(SensorAlarmService.class.getName())) == null) 
					System.out.println("***** Service "+SensorAlarmService.class.getName()+" is not yet ready...");
				SensorAlarmService service14 = (SensorAlarmService)bc.getService(reference14);
				alarm(service14);
			}
		});
		janela.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
			}
		});
		
		JLabel lblDesumificador = new JLabel("Desumificador");
		lblDesumificador.setBounds(10, 63, 134, 29);
		lblDesumificador.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		button_1 = new JButton("-"); button_1.setEnabled(false);
		button_1.setBounds(217, 63, 41, 29);button_1.setFocusable(false);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ServiceReference<?> reference7 = bc.getServiceReference(ActuadorService.class.getName());
				while((reference7 = bc.getServiceReference(ActuadorService.class.getName())) == null) 
					System.out.println("***** Service "+ActuadorService.class.getName()+" is not yet ready...");
				ActuadorService service7 = (ActuadorService)bc.getService(reference7);
				service7.decreaseHum();
				humIntensity.setText(String.valueOf(service7.getIntensityHum()));	
			}
		});
		
		button_2 = new JButton("+"); button_2.setEnabled(false);
		button_2.setBounds(268, 63, 41, 29);button_2.setFocusable(false);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ServiceReference<?> reference8 = bc.getServiceReference(ActuadorService.class.getName());
				while((reference8 = bc.getServiceReference(ActuadorService.class.getName())) == null) 
					System.out.println("***** Service "+ActuadorService.class.getName()+" is not yet ready...");
				ActuadorService service8 = (ActuadorService)bc.getService(reference8);
				service8.increaseHum();
				humIntensity.setText(String.valueOf(service8.getIntensityHum()));	
			}
		});
		
		desumificador = new JToggleButton("OFF"); desumificador.setEnabled(false);
		desumificador.setBounds(142, 63, 65, 29);desumificador.setFocusable(false);
		desumificador.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) 
			{
				ServiceReference<?> reference11 = bc.getServiceReference(ActuadorService.class.getName());
				while((reference11 = bc.getServiceReference(ActuadorService.class.getName())) == null) 
					System.out.println("***** Service "+ActuadorService.class.getName()+" is not yet ready...");
				ActuadorService service11 = (ActuadorService)bc.getService(reference11);
				if (desumificador.isSelected()) {
					desumificador.setText("ON");
					button_1.setEnabled(true);
					button_2.setEnabled(true);
					service11.setHumOn(true);
				}
				else {
					desumificador.setText("OFF");
					button_1.setEnabled(false);
					button_2.setEnabled(false);		
					service11.setHumOn(false);
				}
			}
		});
		panel.setLayout(null);
		panel.add(arCondicionado);
		panel.add(lblNewLabel);
		panel.add(btnNewButton);
		panel.add(button);
		panel.add(desumificador);
		panel.add(lblDesumificador);
		panel.add(button_1);
		panel.add(button_2);
		panel.add(lblWindow);
		panel.add(lblLuz);
		panel.add(janela);
		
		luz = new JToggleButton("OFF"); luz.setEnabled(false);
		luz.setFocusable(false);
		luz.setBounds(142, 103, 65, 29); luz.setFocusable(false);
		luz.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) 
			{
				ServiceReference<?> reference12 = bc.getServiceReference(ActuadorService.class.getName());
				while((reference12 = bc.getServiceReference(ActuadorService.class.getName())) == null) 
					System.out.println("***** Service "+ActuadorService.class.getName()+" is not yet ready...");
				ActuadorService service12 = (ActuadorService)bc.getService(reference12);
				if (luz.isSelected()) {
					luz.setText("ON");
					button_3.setEnabled(true);
					button_4.setEnabled(true);
					service12.setLightOn(true);
				}
				else {
					luz.setText("OFF");
					button_3.setEnabled(false);
					button_4.setEnabled(false);
					service12.setLightOn(false);
				}
			}
		});
		panel.add(luz);
		
		button_3 = new JButton("-"); button_3.setEnabled(false);
		button_3.setFocusable(false);
		button_3.setBounds(217, 103, 41, 29);
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ServiceReference<?> reference8 = bc.getServiceReference(ActuadorService.class.getName());
				while((reference8 = bc.getServiceReference(ActuadorService.class.getName())) == null) 
					System.out.println("***** Service "+ActuadorService.class.getName()+" is not yet ready...");
				ActuadorService service8 = (ActuadorService)bc.getService(reference8);
				if (service8.getIntensityLight() > 0)
					service8.decreaseLight();
				lightIntensity.setText(String.valueOf(service8.getIntensityLight()));	
			}
		});
		panel.add(button_3);
		
		button_4 = new JButton("+"); button_4.setEnabled(false);
		button_4.setFocusable(false);
		button_4.setBounds(268, 103, 41, 29);
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ServiceReference<?> reference9 = bc.getServiceReference(ActuadorService.class.getName());
				while((reference9 = bc.getServiceReference(ActuadorService.class.getName())) == null) 
					System.out.println("***** Service "+ActuadorService.class.getName()+" is not yet ready...");
				ActuadorService service9 = (ActuadorService)bc.getService(reference9);
				if (service9.getIntensityLight() < 100)
					service9.increaseLight();
				lightIntensity.setText(String.valueOf(service9.getIntensityLight()));	
			}
		});
		panel.add(button_4);
		
		AC_intensity = new JLabel("-");
		AC_intensity.setForeground(new Color(128, 0, 0));
		AC_intensity.setFont(new Font("Tahoma", Font.PLAIN, 16));
		AC_intensity.setBounds(319, 23, 41, 29);
		panel.add(AC_intensity);
		
		humIntensity = new JLabel("-");
		humIntensity.setForeground(new Color(0, 0, 128));
		humIntensity.setFont(new Font("Tahoma", Font.PLAIN, 16));
		humIntensity.setBounds(319, 63, 41, 29);
		panel.add(humIntensity);
		
		lightIntensity = new JLabel("-");
		lightIntensity.setForeground(new Color(0, 128, 0));
		lightIntensity.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lightIntensity.setBounds(319, 103, 41, 29);
		panel.add(lightIntensity);
		
		nextTurn = new JButton("Próximo Turno"); nextTurn.setMnemonic(KeyEvent.VK_ENTER);
		nextTurn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Environment.nextTurn();
				Environment.printEnvironmentStatus();
				preenche();
			}
		});
		nextTurn.setFocusable(false);
		nextTurn.setBounds(477, 358, 216, 23);
		contentPane.add(nextTurn);
		
		JPanel alarme = new JPanel();
		alarme.setBorder(new TitledBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, new Color(240, 240, 240), new Color(255, 255, 255), new Color(105, 105, 105), new Color(160, 160, 160)), new LineBorder(new Color(180, 180, 180), 5)), "Estado do Alarme", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(204, 102, 51)));
		alarme.setBounds(10, 202, 370, 179);
		contentPane.add(alarme);
		alarme.setLayout(null);
		
		JLabel label_19 = new JLabel("New label");
		label_19.setIcon(new ImageIcon("HouseManager/icons/warning.png"));
		label_19.setBounds(30, 30, 24, 24);
		alarme.add(label_19);
		
		JLabel label_17 = new JLabel("New label");
		label_17.setIcon(new ImageIcon("HouseManager/icons/warning.png"));
		label_17.setBounds(30, 65, 24, 24);
		alarme.add(label_17);
		
		JLabel label_18 = new JLabel("New label");
		label_18.setIcon(new ImageIcon("HouseManager/icons/warning.png"));
		label_18.setBounds(30, 100, 24, 24);
		alarme.add(label_18);
		
		alarmTemperatura = new JLabel("Temperatura fora dos limites definidos"); alarmTemperatura.setVisible(false);
		alarmTemperatura.setBounds(65, 35, 280, 14);
		alarme.add(alarmTemperatura);
		
		alarmHumidade = new JLabel("Humidade fora dos limites definidos"); alarmHumidade.setVisible(false);
		alarmHumidade.setBounds(65, 70, 280, 14);
		alarme.add(alarmHumidade);
		
		JLabel label_1 = new JLabel("New label");
		label_1.setIcon(new ImageIcon("HouseManager/icons/warning.png"));
		label_1.setBounds(30, 135, 24, 24);
		alarme.add(label_1);
		
		alarmLuz = new JLabel("Luminosidade fora dos limites definidos"); alarmLuz.setVisible(false);
		alarmLuz.setBounds(65, 105, 280, 14);
		alarme.add(alarmLuz);
		
		alarmJanela = new JLabel("Janela não está no estado definido"); alarmJanela.setVisible(false);
		alarmJanela.setBounds(65, 140, 280, 14);
		alarme.add(alarmJanela);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Outside", TitledBorder.RIGHT, TitledBorder.TOP, null, new Color(255, 255, 255)));
		panel_1.setBackground(SystemColor.activeCaption);
		panel_1.setBounds(390, 11, 390, 336);
		contentPane.add(panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setToolTipText("-");
		panel_2.setLayout(null);
		panel_2.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), "Inside", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(10, 40, 195, 227);
		panel_1.add(panel_2);
		
		JLabel label = new JLabel("Temperatura");
		label.setIcon(new ImageIcon("HouseManager/icons/temperature.png"));
		label.setBounds(10, 40, 32, 32);
		panel_2.add(label);
		
		tempIn = new JLabel(String.valueOf("-"));
		tempIn.setHorizontalAlignment(SwingConstants.CENTER);
		tempIn.setFont(new Font("Tahoma", Font.BOLD, 20));
		tempIn.setBounds(50, 40, 100, 32);
		panel_2.add(tempIn);
		
		JLabel label_2 = new JLabel("Humidade");
		label_2.setIcon(new ImageIcon("HouseManager/icons/water.png"));
		label_2.setBounds(10, 100, 32, 32);
		panel_2.add(label_2);
		
		JLabel label_3 = new JLabel("Luminosidade");
		label_3.setIcon(new ImageIcon("HouseManager/icons/light.png"));
		label_3.setBounds(10, 160, 32, 32);
		panel_2.add(label_3);
		
		JLabel label_4 = new JLabel("\u00BAC");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_4.setBounds(160, 40, 32, 32);
		panel_2.add(label_4);
		
		JLabel label_5 = new JLabel("%");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_5.setBounds(160, 100, 32, 32);
		panel_2.add(label_5);
		
		JLabel label_6 = new JLabel("lx");
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_6.setBounds(160, 160, 32, 32);
		panel_2.add(label_6);
		
		humIn = new JLabel("-");
		humIn.setHorizontalAlignment(SwingConstants.CENTER);
		humIn.setVerticalAlignment(SwingConstants.BOTTOM);
		humIn.setFont(new Font("Tahoma", Font.BOLD, 20));
		humIn.setBounds(50, 100, 100, 32);
		panel_2.add(humIn);
		
		lightIn = new JLabel("-");
		lightIn.setHorizontalAlignment(SwingConstants.CENTER);
		lightIn.setFont(new Font("Tahoma", Font.BOLD, 20));
		lightIn.setBounds(50, 160, 100, 32);
		panel_2.add(lightIn);
		
		JLabel label_9 = new JLabel("Humidade");
		label_9.setIcon(new ImageIcon("HouseManager/icons/water2.png"));
		label_9.setBounds(220, 140, 32, 32);
		panel_1.add(label_9);
		
		JLabel label_10 = new JLabel("Humidade");
		label_10.setIcon(new ImageIcon("HouseManager/icons/temperature2.png"));
		label_10.setBounds(220, 80, 32, 32);
		panel_1.add(label_10);
		
		JLabel label_11 = new JLabel("Luminosidade");
		label_11.setIcon(new ImageIcon("HouseManager/icons/solutions2.png"));
		label_11.setBounds(220, 200, 32, 32);
		panel_1.add(label_11);
		
		JLabel label_12 = new JLabel("\u00BAC");
		label_12.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_12.setBounds(360, 80, 32, 32);
		panel_1.add(label_12);
		
		JLabel label_13 = new JLabel("%");
		label_13.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_13.setBounds(360, 140, 32, 32);
		panel_1.add(label_13);
		
		JLabel lblLx = new JLabel("lx");
		lblLx.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblLx.setBounds(360, 200, 32, 32);
		panel_1.add(lblLx);
		
		tempOut = new JLabel("-");
		tempOut.setHorizontalAlignment(SwingConstants.CENTER);
		tempOut.setFont(new Font("Tahoma", Font.BOLD, 20));
		tempOut.setBounds(260, 80, 95, 32);
		panel_1.add(tempOut);
		
		humOut = new JLabel("-");
		humOut.setHorizontalAlignment(SwingConstants.CENTER);
		humOut.setFont(new Font("Tahoma", Font.BOLD, 20));
		humOut.setBounds(260, 140, 95, 32);
		panel_1.add(humOut);
		
		lightOut = new JLabel("-");
		lightOut.setHorizontalAlignment(SwingConstants.CENTER);
		lightOut.setFont(new Font("Tahoma", Font.BOLD, 20));
		lightOut.setBounds(262, 200, 95, 32);
		panel_1.add(lightOut);
	}
}

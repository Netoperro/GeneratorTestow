package forms;

import javax.swing.JFrame;

import units.classes.Test;

import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Label;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;

import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.xml.stream.XMLStreamException;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

public class MainForm
{

	private JFrame frmMainForm;
	private Test NewTest;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MainForm window = new MainForm();
					window.frmMainForm.setVisible(true);
				}
				catch (Exception Ex)
				{
					Ex.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainForm()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		NewTest = new Test();
		frmMainForm = new JFrame();
		frmMainForm.setResizable(false);
		frmMainForm.setTitle("Generator test\u00F3w");
		frmMainForm.setBounds(100, 100, 353, 232);
		frmMainForm.setLocationRelativeTo(null);
		frmMainForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMainForm.getContentPane().setLayout(null);
			
		Label lblQuantityVersions = new Label("Ilo\u015B\u0107 wersji testu");
		lblQuantityVersions.setBounds(65, 13, 102, 24);
		frmMainForm.getContentPane().add(lblQuantityVersions);
		
		Label lblQuantityQuestions = new Label("Iloœæ pytañ na dzial");
		lblQuantityQuestions.setBounds(10, 43, 157, 24);
		frmMainForm.getContentPane().add(lblQuantityQuestions);
			
		JSpinner spnQuantityVersions = new JSpinner();
		spnQuantityVersions.setEnabled(false);
		spnQuantityVersions.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spnQuantityVersions.setBounds(173, 13, 45, 24);
		frmMainForm.getContentPane().add(spnQuantityVersions);
		
		JSpinner spnQuantityQuestions = new JSpinner();
		spnQuantityQuestions.setEnabled(false);
		spnQuantityQuestions.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spnQuantityQuestions.setBounds(173, 43, 45, 24);
		frmMainForm.getContentPane().add(spnQuantityQuestions);
		
		Label lblInformation = new Label("Warto\u015B\u0107 0 oznacza, \u017Ce b\u0119d\u0105 dodane wszystkie pytania");
		lblInformation.setBounds(10, 73, 327, 24);
		frmMainForm.getContentPane().add(lblInformation);
		
		Button btnExportToRTF = new Button("Generuj test");
		btnExportToRTF.setEnabled(false);
		btnExportToRTF.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				FileDialog ExportToRTFFile = new FileDialog(frmMainForm, "Wybierz œcie¿kê i nazwê pliku", FileDialog.SAVE);
				ExportToRTFFile.setLocationRelativeTo(null);
				FilenameFilter filter = new FilenameFilter()
				{		
					@Override					
					public boolean accept(File dir, String name)
					{
						String LowerCaseName = name.toLowerCase();
						if (LowerCaseName.endsWith(".rtf"))
							return true;
						else
							return false;
					}
				};
				ExportToRTFFile.setFilenameFilter(filter);
				ExportToRTFFile.setFile("Test.rtf");
				ExportToRTFFile.setVisible(true);
				try
				{
					NewTest.SetQuantityQuestions(Integer.parseInt(spnQuantityQuestions.getValue().toString()));
					NewTest.SetQuantityVersions(Integer.parseInt(spnQuantityVersions.getValue().toString()));
					
					String FilePath = ExportToRTFFile.getDirectory() + ExportToRTFFile.getFile();
					if (ExportToRTFFile.getFile() != null && !NewTest.ExportToRTF(FilePath))
						JOptionPane.showMessageDialog(frmMainForm, "Eksport do pliku RTF nie powiód³ siê.");
				}
				catch (Exception Ex)
				{
					if (Ex.getMessage().contains("Not enough questions in unit:"))
						JOptionPane.showMessageDialog(frmMainForm, Ex.getMessage());
					else
						JOptionPane.showMessageDialog(frmMainForm, "Eksport do pliku RTF nie powiód³ siê.");
				}
			}
		});
		btnExportToRTF.setBounds(198, 103, 139, 24);
		frmMainForm.getContentPane().add(btnExportToRTF);
		
		Button btnImportXML = new Button("Importuj plik XML");
		btnImportXML.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				FileDialog OpenXMLFile = new FileDialog(frmMainForm, "Wybierz plik Ÿródowy XML", FileDialog.LOAD);
				OpenXMLFile.setLocationRelativeTo(null);
				FilenameFilter filter = new FilenameFilter()
				{		
					@Override					
					public boolean accept(File dir, String name)
					{
						String LowerCaseName = name.toLowerCase();
						if (LowerCaseName.endsWith(".xml"))
							return true;
						else
							return false;
					}
				};
				OpenXMLFile.setFilenameFilter(filter);
				OpenXMLFile.setFile("*.xml");
				OpenXMLFile.setVisible(true);
				String FilePath = OpenXMLFile.getDirectory() + OpenXMLFile.getFile();
				if (OpenXMLFile.getFile() != null)
				{
					try
					{
						if (!NewTest.ImportXMLFile(FilePath))
							JOptionPane.showMessageDialog(frmMainForm, "Import pliku XML nie powiód³ siê.");
						else
						{
							spnQuantityVersions.setEnabled(true);	
							spnQuantityQuestions.setEnabled(true);
							btnExportToRTF.setEnabled(true);
						}
					}
					catch (XMLStreamException Ex)
					{
						JOptionPane.showMessageDialog(frmMainForm, "Import pliku XML nie powiód³ siê.");
					}
					catch (FileNotFoundException Ex)
					{
						JOptionPane.showMessageDialog(frmMainForm, "Import pliku XML nie powiód³ siê.");
					}
				}
			}
		});
		btnImportXML.setBounds(10, 103, 165, 24);
		btnImportXML.setActionCommand("Import XML");
		frmMainForm.getContentPane().add(btnImportXML);
		frmMainForm.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{btnImportXML, btnExportToRTF, spnQuantityVersions, spnQuantityQuestions}));
	}
}

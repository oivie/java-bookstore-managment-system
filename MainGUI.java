package ProductSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainGUI extends JFrame {


	public MainGUI() {
		setTitle("Product Main GUI");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// Creating the label and adding it to the frame
		JLabel label = new JLabel("Product Management System");
		label.setHorizontalAlignment(JLabel.CENTER);
		// Creating font with a larger size and bold style
		Font labelFont = new Font(label.getFont().getName(), Font.BOLD, 24);
		label.setFont(labelFont);
		add(label, BorderLayout.CENTER);

		// Creating a menu bar
		JMenuBar menuBar = new JMenuBar();

		// Creating the File menu
		JMenu fileMenu = new JMenu("File");
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(exitMenuItem);

		// Creating the Product menu and handling the events
		JMenu productMenu = new JMenu("Product");
		JMenuItem addUpdateMenuItem = new JMenuItem("Add/Update");
		JMenuItem findDisplayMenuItem = new JMenuItem("Find/Display");

		addUpdateMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Opening the Add/Update window
				AddUpdateWindow addUpdateWindow = new AddUpdateWindow();
				addUpdateWindow.setVisible(true);
			}
		});

		findDisplayMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Opening the Find/Display window
				FindDisplayWindow findDisplayWindow = new FindDisplayWindow();
				findDisplayWindow.setVisible(true);
			}
		});

		productMenu.add(addUpdateMenuItem);
		productMenu.add(findDisplayMenuItem);

		// Adding File and Product menus to the menu bar
		menuBar.add(fileMenu);
		menuBar.add(productMenu);

		// Setting the menu bar for the frame
		setJMenuBar(menuBar);
	}

	public static void main(String[] args) {
		MainGUI mainGUI = new MainGUI();
		mainGUI.setVisible(true);
		mainGUI.setResizable(false);

	}
}


class AddUpdateWindow extends JFrame {
	private final String filename = "src/ProductSystem/data.bin";
	File file = new File(filename);
	private JTextField idField, nameField, priceField,quantityField;
	private JTextArea descriptionArea;

	public AddUpdateWindow() {
		setTitle("Add/Update Product");
		setSize(500, 300);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		// Creating the main panel with a GridLayout
		JPanel mainPanel = new JPanel(new GridLayout(5, 1));
		// Creating an EmptyBorder to create a gap
		EmptyBorder emptyBorder = new EmptyBorder(13, 13, 13, 13);
		mainPanel.setBorder(emptyBorder);

		// First Row
		JPanel firstRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		firstRow.add(new JLabel("Product ID                "));
		idField = new JTextField(20); 
		firstRow.add(idField);

		// Second Row
		JPanel secondRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		secondRow.add(new JLabel("Name                        "));
		nameField = new JTextField(20);
		secondRow.add(nameField);

		// Third Row
		JPanel thirdPanel = new JPanel(new GridLayout(1, 4));

		JPanel descriptionPanel = new JPanel(new GridLayout(1, 2));
		JLabel descriptionLabel = new JLabel("Description ");
		descriptionPanel.add(descriptionLabel);
		descriptionArea = new JTextArea(4, 20);
		descriptionPanel.add(descriptionArea);
		JPanel quantityPricePanel = new JPanel(new GridLayout(2, 2));

		quantityPricePanel.add(new JLabel("  Quantity in hand "));
		quantityField = new JTextField(5);
		quantityPricePanel.add(quantityField);
		quantityPricePanel.add(new JLabel("  Unit Price "));
		priceField = new JTextField(5);
		quantityPricePanel.add(priceField);
		descriptionArea.setBorder(emptyBorder);
		EmptyBorder emptyBorder1 = new EmptyBorder(5, 5, 5, 5);
		quantityPricePanel.setBorder(emptyBorder1);

		thirdPanel.add(descriptionPanel);
		thirdPanel.add(quantityPricePanel);

		// Fourth Row
		JPanel fourthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,25,0));
		EmptyBorder emptyBorder2 = new EmptyBorder(20, 0, 0, 0);
		fourthPanel.setBorder(emptyBorder2);
		JButton addButton = new JButton("  Add  ");
		JButton updateButton = new JButton("  Update  ");
		fourthPanel.add(addButton);
		fourthPanel.add(updateButton);

		// Fifth Row
		JPanel fifthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,25,0));
		EmptyBorder emptyBorder3 = new EmptyBorder(20, 0, 10, 20);
		fifthPanel.setBorder(emptyBorder3);

		JButton firstButton = new JButton("  First  ");
		JButton previousButton = new JButton("  Previous  ");
		JButton nextButton = new JButton("    Next    ");
		JButton lastButton = new JButton("    Last    ");


		fifthPanel.add(firstButton);
		fifthPanel.add(previousButton);
		fifthPanel.add(nextButton);
		fifthPanel.add(lastButton);

		// Adding subpanels and components to the main panel
		mainPanel.add(firstRow);
		mainPanel.add(secondRow);
		mainPanel.add(thirdPanel);
		mainPanel.add(fourthPanel);
		mainPanel.add(fifthPanel);

		// Adding the main panel to the Add/Update window
		add(mainPanel);

		// Adding action listeners for navigation buttons
		firstButton.addActionListener(e -> getFirstProduct1());
		previousButton.addActionListener(e -> getPreviousProduct());
		nextButton.addActionListener(e -> getNextProduct());
		lastButton.addActionListener(e -> getLastProduct());
		updateButton.addActionListener(e -> updateProductDetails());
		addButton.addActionListener(e -> addProductDetails());        
	}

	// feedback in the form of a JOptionPane based on the validation results.
	
	// Worked on the implementation of the writing data to the file and creating the file if not exists.
	private void addProductDetails() {
		try {
			if (file.createNewFile()) {
			} else {
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		String id = idField.getText().trim();
		String name = nameField.getText().trim();
		String price = priceField.getText().trim();
		String quantity = quantityField.getText().trim();
		String desc = descriptionArea.getText().trim();

		
		if (id.isEmpty() || name.isEmpty() || price.isEmpty() || quantity.isEmpty() || desc.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Error! All fields must be filled out.");
			return;
		}

		if (!isValidId(id)) {
			JOptionPane.showMessageDialog(null, "Error! Please enter a valid ID.");
			return;
		}

		if (!isUniqueId(id)) {
			JOptionPane.showMessageDialog(null, "Error! ID already exists. Please enter a unique ID.");
			return;
		}

		if (!isValidName(name)) {
			JOptionPane.showMessageDialog(null, "Error! Please enter a valid product name.");
			return;
		}

		if (!isValidName(desc)) {
			JOptionPane.showMessageDialog(null, "Error! Please enter a valid Description.");
			return;
		}

		if (!isValidNumber(price)) {
			JOptionPane.showMessageDialog(null, "Error! Please enter a valid price.");
			return;
		}

		if (!isValidNumber(quantity)) {
			JOptionPane.showMessageDialog(null, "Error! Please enter a valid quantity.");
			return;
		}
		// E out

		// If all validations pass, then proceed to save the data to the file.
		try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(file, true))) {
			String dataToWrite = id + "|" + name + "|" + price + "|" + quantity + "|"+ desc+ "\n";
			outputStream.writeUTF(dataToWrite);
			JOptionPane.showMessageDialog(null, "Data saved to file successfully.");
			//clearing the text in various fields and areas
			idField.setText("");
			nameField.setText("");
			priceField.setText("");
			quantityField.setText(""); 
			descriptionArea.setText("");
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error saving data to file: " + e.getMessage());
		}
	}


	//Before adding a new product, we should check if the ID already exists in the file:
	private boolean isUniqueId(String id) {
		try (DataInputStream inputStream = new DataInputStream(new FileInputStream(file))) {
			String currentRecord;
			while ((currentRecord = readNextRecord(inputStream)) != null) {
				String[] values = currentRecord.split("\\|");
				if (values.length > 0 && values[0].equals(id)) {
					return false;  // ID already exists
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error reading from the file!");
			return false; // Return false if there's an error
		}
		return true;  // ID is unique
	}

	// For the ID:
	private boolean isValidId(String id) {
		try {
			Integer.parseInt(id);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	// For the Product Name:
	private boolean isValidName(String name) {
		// names should only contain alphabets and spaces
		return name.matches("[a-zA-Z ]+");
	}

	// 	For the Unit Price and Quantity:
	private boolean isValidNumber(String number) {
		try {
			Double.parseDouble(number);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private void getFirstProduct1() {
		try (DataInputStream inputStream = new DataInputStream(new FileInputStream(file)))
		{
			String firstLine = inputStream.readUTF();

			String[] values = firstLine.split("\\|");
			String value1 = values.length >= 1 ? values[0] : "";
			String value2 = values.length >= 2 ? values[1] : "";
			String value3 = values.length >= 3 ? values[2] : "";
			String value4 = values.length >= 4 ? values[3] : "";
			String value5 = values.length >= 5 ? values[4] : "";

			// Splitting the line by commas and get values into an array
			idField.setText(value1); // values[0] is the first value
			nameField.setText(value2); // values[1] is the second value
			priceField.setText(value3); // values[2] is the third value
			quantityField.setText(value4);
			descriptionArea.setText(value5);
			// Reading the first line from the file

		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File not found: " + e.getMessage());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error reading from file..Empty!");
		}
	}

	private void getLastProduct() {
		// Implementing logic to retrieve the last product from the file
		try (DataInputStream inputStream = new DataInputStream(new FileInputStream(file))) {
			// Reading the binary file
			String line;
			String lastLine = null;

			// Reading lines from the file until reached null ie EOF
			while ((line = readNextRecord(inputStream)) != null) {
				lastLine = line;
			}

			if (lastLine != null) {
				String[] values = lastLine.split("\\|");
				String value1 = values.length >= 1 ? values[0] : "";
				String value2 = values.length >= 2 ? values[1] : "";
				String value3 = values.length >= 3 ? values[2] : "";
				String value4 = values.length >= 4 ? values[3] : "";
				String value5 = values.length >= 5 ? values[4] : "";

				// Setting the values to the fields
				idField.setText(value1);      // values[0] is the first value
				nameField.setText(value2);    // values[1] is the second value
				priceField.setText(value3);   // values[2] is the third value
				quantityField.setText(value4);
				descriptionArea.setText(value5);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "The File is empty!");
			}
			// Closing the resources
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File not found: " + e.getMessage());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error reading from file..empty");
		}
	}

		
	//Method to get the Previous product
		private void getPreviousProduct() {
		if (!idField.getText().isEmpty() && !nameField.getText().isEmpty() && !priceField.getText().isEmpty() && !quantityField.getText().isEmpty()) {
			String currentData = idField.getText().trim().stripLeading() + "|" +
					nameField.getText().trim() + "|" +
					priceField.getText().trim() + "|" +
					quantityField.getText().trim();

			boolean foundMatch = false;
	//Reading the data from an input stream, comparing it with the current data, and if a match is found, it processes the previous record
			try (DataInputStream inputStream = new DataInputStream(new FileInputStream(file))) {
				String currentRecord;
				String previousRecord = null;

				while ((currentRecord = inputStream.readUTF()) != null) {
					if (currentRecord.contains(currentData)) {
						if (previousRecord != null) {
							String[] values = previousRecord.split("\\|");
							String value1 = values.length >= 1 ? values[0] : "";
							String value2 = values.length >= 2 ? values[1] : "";
							String value3 = values.length >= 3 ? values[2] : "";
							String value4 = values.length >= 4 ? values[3] : "";
							String value5 = values.length >= 5 ? values[4] : "";


							// Setting the values to the fields
							idField.setText(value1);      // values[0] is the first value
							nameField.setText(value2);    // values[1] is the second value
							priceField.setText(value3);   // values[2] is the third value
							quantityField.setText(value4);
							descriptionArea.setText(value5);
						} else {
							JOptionPane.showMessageDialog(null, "The values in the textfield are the first record in the file. No previous record found!");
							idField.setText("");     
							nameField.setText("");    
							priceField.setText("");  
							quantityField.setText("");
							descriptionArea.setText("");
						}

						foundMatch = true;
						break;  // Exiting the loop when a match is found
					}
					previousRecord = currentRecord;
				}
			} catch (EOFException e) {
				// End of file reached, do nothing
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Error reading from file..empty!");
			}

			if (!foundMatch) {
				JOptionPane.showMessageDialog(null, "No matching record with the same details found!");
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Please enter the details in textbox for which you want to search the previous record!");
		}
	}


		//Method to get Next product
	private void getNextProduct() {
		if (!idField.getText().isEmpty() && !nameField.getText().isEmpty() && !priceField.getText().isEmpty() && !quantityField.getText().isEmpty() && !descriptionArea.getText().isEmpty()) {
			try (DataInputStream inputStream = new DataInputStream(new FileInputStream(file))) {
				String currentData = idField.getText().trim().stripLeading() + "|" +
						nameField.getText().trim() + "|" +
						priceField.getText().trim() + "|" +
						quantityField.getText().trim() + "|" +
						descriptionArea.getText().trim();

				String currentRecord = readNextRecord(inputStream);


				while (currentRecord != null) {
					if (currentRecord.contains(currentData)) {
						// Reading the next record if the current record matches the data
						currentRecord = readNextRecord(inputStream);
						if (currentRecord != null) {
							// Splitting the next record and setting the values to the fields
							String[] values = currentRecord.split("\\|");
							String value1 = values.length >= 1 ? values[0] : "";
							String value2 = values.length >= 2 ? values[1] : "";
							String value3 = values.length >= 3 ? values[2] : "";
							String value4 = values.length >= 4 ? values[3] : "";
							String value5 = values.length >= 5 ? values[4] : "";


							// Setting the values to the fields
							idField.setText(value1);
							nameField.setText(value2);
							priceField.setText(value3);
							quantityField.setText(value4);
							descriptionArea.setText(value5);

							// Breaking the loop after finding the next record
							break;
						}
						else {
							JOptionPane.showMessageDialog(null, "The values in the textfield are the last record in the file. No Next record found!");
							idField.setText("");      
							nameField.setText("");    
							priceField.setText("");   
							quantityField.setText("");
							descriptionArea.setText("");
						}
					}
					currentRecord = readNextRecord(inputStream);
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Error reading from file: " + e.getMessage());
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Please enter the details in textbox for which you want to search the next record!");
		}
	}

	private String readNextRecord(DataInputStream inputStream) throws IOException {
		try {
			// Reading the next record from the binary file
			return inputStream.readUTF();
		} catch (EOFException e) {
			// Ending of file reached, return null
			return null;
		}
	}


	//updating the current record in the binary file that stores product detail
	private void updateProductDetails() {
		String id = idField.getText().trim();
		String name = nameField.getText().trim();
		String price = priceField.getText().trim();
		String quantity = quantityField.getText().trim();
		String desc = descriptionArea.getText().trim();

		// input validation to ensure that all required fields (Product ID, Name, Price, Quantity, and Description) are filled out
		if (id.isEmpty() || name.isEmpty() || price.isEmpty() || quantity.isEmpty() || desc.isEmpty()) {
	        // Showing an error message if any required field is empty
			JOptionPane.showMessageDialog(null, "Error! All fields must be filled out.");
			return;
		}

		try {
			// reading all the records from the binary file (data.bin) and storing them in a list using a DataInputStream.
			List<String> records = new ArrayList<String>();
			try (DataInputStream inputStream = new DataInputStream(new FileInputStream(file))) {
				while (true) {
					String record = inputStream.readUTF();
					records.add(record);
				}
			} catch (EOFException eof) {
				// End of file reached, do nothing
			}

			// Finding and updating the record with the matching ID
			boolean found = false;
			for (int i = 0; i < records.size(); i++) {
				String record = records.get(i);
				String[] values = record.split("\\|");
				if (values.length > 0 && values[0].equals(id)) {
					// Replacing the record with the updated values
					records.set(i, id + "|" + name + "|" + price + "|" + quantity+ "|" +desc+"\n");
					found = true;
					break;
				}
			}

			// Writing all the updated records back to the file
			try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(file, false))) {
				for (String record : records) {
					outputStream.writeUTF(record);
				}
			}

			// Displaying a message based on whether the update was successful
			if (found) {
				JOptionPane.showMessageDialog(null, "Product details updated successfully.");
			} else {
				JOptionPane.showMessageDialog(null, "Error! Product with ID " + id + " not found.");
			}
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error updating product details: " + e.getMessage());
		}
	}



}

class FindDisplayWindow extends JFrame {
	private JTextField keywordfind,from,to;
	private JTextArea details;
	private JRadioButton pricerange , keywordradio, all;
	private final String filename = "src/ProductSystem/data.bin";
	File file = new File(filename);

	public FindDisplayWindow() {

		setTitle("Find/Display Products");
		setSize(550, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		// Creating a BigPanel
		JPanel bigPanel = new JPanel(new BorderLayout());
		//Creating a gap between margin and panel like the screenshot
		EmptyBorder emptyBorder = new EmptyBorder(13, 13, 25, 13);
		bigPanel.setBorder(emptyBorder);


		// Creating the top panel with a grid layout (3 columns)
		JPanel topPanel = new JPanel(new GridLayout(1, 3));

		// First Column - Radio Buttons
		JPanel firstColumnPanel = new JPanel(new GridLayout(3, 1));

		pricerange = new JRadioButton("Price Range");
		keywordradio = new JRadioButton("Keyword");
		all = new JRadioButton("All");


		// Adding radio buttons to the first column panel
		firstColumnPanel.add(pricerange);
		firstColumnPanel.add(keywordradio);
		firstColumnPanel.add(all);

		// Creating a button group for radio buttons
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(pricerange);
		buttonGroup.add(keywordradio);
		buttonGroup.add(all);

		// Second Column - Text Fields
		JPanel secondColumnPanel = new JPanel(new GridLayout(3, 2,15,15));

		to = new JTextField(20);
		to.setText("to");
		keywordfind = new JTextField(20);
		keywordfind.setText("Keyword");
		JLabel empty = new JLabel();
		from = new JTextField(20);
		from.setText("from");

		// Adding text fields to the second column panel (An extra empty label to make it as close as possible to the screenshot requirement
		secondColumnPanel.add(to);
		secondColumnPanel.add(from);
		secondColumnPanel.add(keywordfind);
		secondColumnPanel.add(empty);


		// Third Column - Find/Display Button
		JPanel thirdColumnPanel = new JPanel();
		JButton findplay = new JButton("Find/Display");

		// Adding Find/Display button to the third column panel
		thirdColumnPanel.add(findplay);

		// Adding first, second, and third column panels to the top panel
		topPanel.add(firstColumnPanel);
		topPanel.add(secondColumnPanel);
		topPanel.add(thirdColumnPanel);

		// Creating the bottom panel with a large text area
		JPanel bottomPanel = new JPanel(new BorderLayout());
		details = new JTextArea();

		// Adding the text area to the bottom panel
		bottomPanel.add(new JScrollPane(details));

		// Adding the top and bottom panels to the BigPanel
		bigPanel.add(topPanel, BorderLayout.NORTH);
		bigPanel.add(bottomPanel, BorderLayout.CENTER);

		// Adding the BigPanel to the Find/Display Products window
		add(bigPanel);

		
		//Action listeners for button
		findplay.addActionListener(e -> {
			try {
				findplayDetails();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "An error occurred while finding product details: " + e1.getMessage());
			}
		});
	}



	private void findplayDetails() throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		if(file.exists() && file.length() > 0)
		{
			if (keywordradio.isSelected()) {
				if(! keywordfind.getText().isEmpty()) {
					String keyword = keywordfind.getText().trim();

					// Logic to search the binary file based on the provided keyword


					try (DataInputStream inputStream = new DataInputStream(new FileInputStream(file))) {
						details.setText(""); // Clearing previous results
						while (true) {
							try {
								String line = inputStream.readUTF().trim(); // Reading and trim the line
								// If the trimmed line contains the trimmed keyword (case-insensitive), displaying it in the resultTextArea
								if (line.toLowerCase().contains(keyword.toLowerCase())) {
									details.append(line + "\n"); // Display the matching line
									keywordfind.setText(""); // Clearing the keyword input field after finding a match
									return; // Exiting the loop if a match is found
								}
							} catch (EOFException eof) {
								break; // Ending of file reached
							}
						}
						// If no match is found after reading the entire file
						JOptionPane.showMessageDialog(null, "No match found in the file to display!");
						keywordfind.setText(""); // Clearing the keyword input field after finding a match

					} catch (IOException ex) {
						ex.printStackTrace();
					}

				}
				else
				{
					JOptionPane.showMessageDialog(null, "Error! please enter the keyword to find in the file!");
				}
			}
			else if (all.isSelected()) {
				try (DataInputStream inputStream = new DataInputStream(new FileInputStream(file))) {
					details.setText(""); // Clearing previous results
					while (true) {
						try {
							String line = inputStream.readUTF();
							if(line.length() > 0)
							{
								details.append(line + "\n"); // Displaying the matching line
							}
							else
							{
								JOptionPane.showMessageDialog(null, "The file is empty..nothing to display!");
							}
						} catch (EOFException eof) {
							break; // End of file reached
						}
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			else if (pricerange.isSelected()) {
				if(!to.getText().isEmpty() && !from.getText().isEmpty())
				{
					try (DataInputStream inputStream = new DataInputStream(new FileInputStream(file))) {
						details.setText(""); // Clearing previous results
						double minPrice = Double.parseDouble(to.getText());
						double maxPrice = Double.parseDouble(from.getText());

						while (true) {
							try {
								String line = inputStream.readUTF();
								// Extracting price from the line
								String[] values = line.split("\\|");
								if (values.length >= 1) {
									double price = Double.parseDouble(values[2]);
									// Checking if the price falls within the specified range
									if (price >= minPrice  && price <= maxPrice) {
										details.append(line + "\n"); // Displaying the matching line
										to.setText("");
										from.setText("");
									}
								}
							} catch (EOFException e) {
								break;
							}
						}
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Error! please enter the range textfields to search.");
				}
			}//outer else if loop
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Error! file does not exists or is empty!");
		}
	}

}


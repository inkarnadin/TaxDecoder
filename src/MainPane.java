package kurz.java.taxdecoder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

public class MainPane extends VBox
{
    private TextField findText;
    private TextField convertDestPath;
    private TextField sourcePath;
    
    private TableView<ResultStore> table;
    
    public MainPane()
    {
        init();  
    }

    private void init()
    {        
        VBox container = new VBox();
        container.setFillWidth(true);
        container.getChildren().addAll(createFindContainer(), createConverterContainer(), createPathContainer());        
                
        this.table = new TableView<>();
        VBox.setVgrow(table, Priority.ALWAYS);
        
        TableColumn<ResultStore, String> dateCreateColumn = new TableColumn<>("Дата создания");
        dateCreateColumn.setCellValueFactory(new PropertyValueFactory<>("dateCreate"));   
        dateCreateColumn.setMinWidth(80.0);
        
        TableColumn<ResultStore, String> dateDocumentColumn = new TableColumn<>("Дата документа");
        dateDocumentColumn.setCellValueFactory(new PropertyValueFactory<>("dateDocument"));
        dateDocumentColumn.setMinWidth(80.0);
        
        TableColumn<ResultStore, String> idDocColumn = new TableColumn<>("ИД документа");
        idDocColumn.setCellValueFactory(new PropertyValueFactory<>("idDoc"));
        idDocColumn.setMinWidth(200.0);
        
        TableColumn<ResultStore, String> innULColumn = new TableColumn<>("ИНН");
        innULColumn.setCellValueFactory(new PropertyValueFactory<>("innUL"));
        innULColumn.setMinWidth(80.0);
        
        TableColumn<ResultStore, String> orgNameColumn = new TableColumn<>("Название организации");
        orgNameColumn.setCellValueFactory(new PropertyValueFactory<>("orgName"));
        orgNameColumn.setMinWidth(350.0);
        
        TableColumn<ResultStore, String> incomeColumn = new TableColumn<>("Сумма дохода");
        incomeColumn.setCellValueFactory(new PropertyValueFactory<>("income"));
        incomeColumn.setMinWidth(80.0);
        
        TableColumn<ResultStore, String> expenseColumn = new TableColumn<>("Сумма расхода");
        expenseColumn.setCellValueFactory(new PropertyValueFactory<>("expense"));
        expenseColumn.setMinWidth(80.0);
        
        this.table.getColumns().addAll(dateCreateColumn, dateDocumentColumn, idDocColumn, innULColumn, orgNameColumn, incomeColumn, expenseColumn);
        
        SplitPane splitPane = new SplitPane(container, this.table);
        splitPane.setDividerPosition(0, 0.1);
        splitPane.setOrientation(Orientation.VERTICAL);
        
        getChildren().addAll(splitPane);
    }
    
    private HBox createFindContainer()
    {      
        Button findButton = new Button();
        findButton.setText("Поиск по ИНН"); 
        findButton.setMinWidth(200);            
        findButton.setOnAction(e -> findByINN(FileIO.getFileList(this.sourcePath.getText())));        
               
        this.findText = new TextField();
        this.findText.setMinWidth(150.0);

        HBox subContainer = new HBox();    
        subContainer.setSpacing(10);
        subContainer.setPadding(new Insets(5.0, 0.0, 0.0, 5.0));
        subContainer.getChildren().addAll(findButton, this.findText);

        return subContainer;
    }
    
    private HBox createConverterContainer()
    {
        Button convertButton = new Button();
        convertButton.setText("Получить обобщенный файл"); 
        convertButton.setMinWidth(200.0);            
        convertButton.setOnAction(e -> parse(FileIO.getFileList(this.sourcePath.getText())));  
        
        this.convertDestPath = new TextField("result.csv");
        this.convertDestPath.setMinWidth(150.0);   
        
        Label notes = new Label("(Процесс может занять несколько минут)");        
        
        HBox subContainer = new HBox();    
        subContainer.setSpacing(10);
        subContainer.setPadding(new Insets(0.0, 0.0, 0.0, 5.0));
        subContainer.getChildren().addAll(convertButton, this.convertDestPath, notes);
        
        return subContainer;
    }
    
    private HBox createPathContainer()
    {
        Button pathButton = new Button();
        pathButton.setText("Путь к XML-файлам"); 
        pathButton.setMinWidth(200.0); 
        pathButton.setDisable(true);
        
        this.sourcePath = new TextField("targetDir_");
        this.sourcePath.setMinWidth(450.0); 
        
        HBox subContainer = new HBox();    
        subContainer.setSpacing(10);
        subContainer.setPadding(new Insets(0.0, 0.0, 0.0, 5.0));
        subContainer.getChildren().addAll(pathButton, this.sourcePath);
        
        return subContainer;
    }
    
    private void findByINN(List<File> fileList)
    {
        String inn = this.findText.getText();
        List<ResultStore> rsList = new ArrayList<>();
        for (File file : fileList)
        {
            List<ResultStore> resourceList = XMLParser.parse(file.getAbsoluteFile().getPath());
            resourceList.stream()
                    .filter(r -> r.compareINN(inn))
                    .forEach(x -> rsList.add(x));
        }
        this.table.setItems(FXCollections.observableArrayList(rsList));
    }

    private void parse(List<File> fileList)
    {
        fileList.forEach(f -> FileIO.saveToCsv(XMLParser.parse(f.getAbsoluteFile().getPath()), this.convertDestPath.getText()));
    } 
}

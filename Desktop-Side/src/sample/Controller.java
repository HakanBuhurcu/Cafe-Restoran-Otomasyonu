package sample;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.beans.InvalidationListener;
import javafx.beans.NamedArg;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.lang.String;
import java.awt.*;
import java.awt.Font;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.sql.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.List;


public class Controller {

    /* @TODO ***************   login.fxml   *************** */
    @FXML
    TextField ssn;
    @FXML
    PasswordField passwd;
    @FXML
    VBox scene;
    @FXML
    Text userName;
    @FXML
    ChoiceBox<String> categoryChoise;
    @FXML
    HBox categoryBox;
    @FXML
    Button editBranchButon,openShiftList;
    @FXML
     TableView<Shift> shiftTable;
    @FXML
    TableColumn<Shift,String> sdateCol, stcCol,snameCol,stimeCol;

    StringBuffer response = null;

    public void openShiftListt(MouseEvent mouseEvent)throws IOException{
        changeLayout("ShiftList");
        Shift s1 = new Shift("27/07/2017","24241517895","Mehmet YILDIRIM","08:30-17:00");
        Shift s2 = new Shift("27/07/2017","24244717895","Emre DEMİR","08:30-17:00");
        Shift s3 = new Shift("27/07/2017","24411517895","Selin KAYA","08:30-17:00");
        Shift s4 = new Shift("27/07/2017","24275517895","Efe YILMAZ","08:30-17:00");

        Shift s5 = new Shift("27/07/2017","15475268412","Ayşe KILIÇ","17:00-23:30");
        Shift s6 = new Shift("27/07/2017","15414268412","Serdar TOK","17:00-23:30");
        Shift s7 = new Shift("27/07/2017","15447268412","Zeynep TEK","17:00-23:30");
        Shift s8 = new Shift("27/07/2017","15475268562","Mustafa DENİZ","17:00-23:30");

        Shift s9 = new Shift("28/07/2017","24241517895","Mehmet YILDIRIM","17:00-23:30");
        Shift s10 = new Shift("28/07/2017","24244717895","Emre DEMİR","17:00-23:30");
        Shift s11 = new Shift("28/07/2017","24411517895","Selin KAYA","17:00-23:30");
        Shift s12 = new Shift("28/07/2017","24275517895","Efe YILMAZ","17:00-23:30");

        Shift s13 = new Shift("28/07/2017","15475268412","Ayşe KILIÇ","08:30-17:00");
        Shift s14 = new Shift("28/07/2017","15414268412","Serdar TOK","08:30-17:00");
        Shift s15 = new Shift("28/07/2017","15447268412","Zeynep TEK","08:30-17:00");
        Shift s16 = new Shift("28/07/2017","15475268562","Mustafa DENİZ","08:30-17:00");

        sdateCol.setCellValueFactory(new PropertyValueFactory<Shift,String>("tarih"));
        stcCol.setCellValueFactory(new PropertyValueFactory<Shift,String>("personelTc"));
        snameCol.setCellValueFactory(new PropertyValueFactory<Shift,String>("personelisim"));
        stimeCol.setCellValueFactory(new PropertyValueFactory<Shift,String>("saat"));

        shiftTable.getItems().addAll(s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13,s14,s15,s16);
    }

    public void doLogin(MouseEvent mouseEvent) throws IOException {
        String ssnText = ssn.getText();
        String passwdText = passwd.getText();

        response = new Common().doHttpPost(Common.baseURL+"index.php?employeeTcNo="+ssnText+"&employeePasswd="+passwdText);

        JsonObject jsonObject = new JsonParser().parse(String.valueOf(response)).getAsJsonObject();
        if(jsonObject.get("login").getAsString().equals("ok")){ // giriş başarılı

            Common.setEmployeeTcNo(ssnText); // Birazdan TC numarasına göre, User'ın Employee Tablosundan verileri çekilecek.

            response = new Common().doHttpPost(Common.baseURL+"getEmployee.php");
            JsonArray employeeArr = new JsonParser().parse(String.valueOf(response)).getAsJsonArray();

            for (int i = 0; i < employeeArr.size(); i++){
                JsonObject employeeObj = employeeArr.get(i).getAsJsonObject();

                String fetchedEmployeeTcNo = employeeObj.get("employeeTcNo").getAsString();

                if (Objects.equals(fetchedEmployeeTcNo, Common.getEmployeeTcNo())){ // User ile Employee eşleşmesi sağlandı.

                    Common.setEmployeeName(employeeObj.get("employeeName").getAsString());
                    Common.setEmployeeStatu(employeeObj.get("statuID").getAsInt());

                    String path = null;
                    if (Common.getEmployeeStatu() != 2 && Common.getEmployeeStatu() !=3) {// Sisteme giriş yapan kişi yönetici DEĞİL.
                        path = "subUI/addOrder.fxml";
                    }else {
                        path = "home.fxml";
                    }

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
                    fxmlLoader.setController(this);
                    Stage stage = (Stage) scene.getScene().getWindow();
                    stage.setTitle("Petronet Kontrol Paneli");
                    Scene scene = new Scene(fxmlLoader.load());
                    scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                    stage.setScene(scene);
                    stage.show();

                    if(Common.getEmployeeStatu() == 3){
                        leftBar.getChildren().removeAll(addproduct,updateproduct,addstatu,editcategory,editBranchButon);
                    }

                    if(Common.getEmployeeStatu()== 2){
                        leftBar.getChildren().removeAll(openAddOrderButton,createShiftButon,updateTableButon);
                    }

                    if (Common.getEmployeeStatu() != 2 && Common.getEmployeeStatu()!= 3){

                        container.getChildren().removeAll(orderContainer);

                        HBox topBar = new HBox();

                        HBox leftSide = new HBox();
                            leftSide.prefWidthProperty().bind(stage.widthProperty().divide(2));
                        Text welcome = new Text("Hoşgeldin, ");
                            welcome.setText(welcome.getText()+Common.getEmployeeName() + Common.getEmployeeStatu());
                            welcome.setFont(new javafx.scene.text.Font("Calibri Bold",17));
                            welcome.getStyleClass().add("my-text");

                        leftSide.getChildren().add(welcome);

                        HBox rightSide = new HBox();
                            rightSide.prefWidthProperty().bind(stage.widthProperty().divide(2));
                        Text logOut = new Text("Çıkış Yap");
                            logOut.setTextAlignment(TextAlignment.RIGHT);
                            logOut.wrappingWidthProperty().bind(stage.widthProperty().divide(2));
                        logOut.setFont(new javafx.scene.text.Font("Calibri",17));
                        logOut.getStyleClass().add("my-text");
                        rightSide.getChildren().add(logOut);

                        logOut.setOnMouseClicked((MouseEvent clicked) -> {

                            try {
                                Stage primaryStage = (Stage) container.getScene().getWindow();

                                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                                primaryStage.setTitle("Giriş Yap!");
                                Scene sceneInner = new Scene(root);
                                sceneInner.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                                primaryStage.setScene(sceneInner);
                                primaryStage.show();

                                primaryStage.setMinHeight(primaryStage.getHeight());
                                primaryStage.setMinWidth(primaryStage.getWidth());

                                new Common().createLog(Common.getEmployeeName() +" sistemden çıkış yaptı");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });

                        topBar.getChildren().addAll(leftSide,rightSide);
                        container.getChildren().add(0,topBar);

                        // gerisi openAddOrder() ile aynı.

                        response = new Common().doHttpPost(Common.baseURL+"getTableCount.php");


                        System.out.println("Layoutun toplam genişliği: "+stage.getWidth());
                        System.out.println("Masa sayısı: "+Integer.parseInt(String.valueOf(response).trim()));

                        int numberOfTable = Integer.parseInt(String.valueOf(response).trim());
                        int tableNumber = 1;
                        int numberOfTableInLayout = (int) (stage.getWidth()/100);
                        // Masa sayısı 13, satırdaki masa sayısı 7 olursa satır sayısı 2 çıkması gerekiyorken 1 çıkıyor.
                        // Java 2. satırı, işlemi aşağı yuvarladığı için yok ediyor. Bu yüzden işlemin sonucunu yukarı yuvarladım.
                        // Ayrıca işlemin sonucunun yuvarlanabilir olması için int değil double kullandım.
                        int numberOfRow = (int) Math.ceil((double) numberOfTable/(double) numberOfTableInLayout);
                        System.out.println("Bir satırdaki masa sayısı: "+numberOfTableInLayout+"\nSatır sayısı: "+numberOfRow);

                        for (int b = 0; b < numberOfRow; b++){

                            HBox tableRow = new HBox(); // Masaları tutan taşıyıcı.
                            tableRow.setPrefHeight(100);
                            tableRow.setMaxWidth(numberOfTableInLayout*100);
                            tableRow.setId("row"+b);

                            for (int j = 0; j < numberOfTableInLayout; j++){
                                javafx.scene.control.Button tablePane = new javafx.scene.control.Button();
                                tablePane.setId("tableButton");
                                tablePane.setText(tableNumber+""); // Masa numaraları burada bastırılıyor.
                                tablePane.setAlignment(Pos.CENTER);
                                tablePane.setPrefWidth(100);
                                tablePane.setPrefHeight(100);
                                tableRow.getChildren().add(tablePane);

                                // bir masa seçildi.
                                tablePane.setOnMouseClicked((MouseEvent e) -> {
                                    Controller.SELECTED_TABLE_NUMBER = Integer.parseInt(tablePane.getText());
                                    productContainer.getChildren().removeAll(productContainer.getChildren()); // Tekrar masa seçimi yapıldığında tekrarlanan veri olmaması için

                    /*
                        Aşağıdaki kod satırı ile istediğim zaman masa şemasını aynı yere koyabiliyorum.
                        container.getChildren().add(1,tableContainer);

                        Tekrar masa seçim ekranına dönüldüğünde aşağıdaki işlemlerin tam tersi yapılacak.
                    */
                                    container.getChildren().remove(tableContainer); // masa sahnesi ekrandan kaldırılıyor.
                                    container.getChildren().remove(complaintButonContainer);
                                    container.getChildren().remove(complaintContainer);
                                   // container.getChildren().add(categoryBox
                                  //  productContainer.getChildren().add(categoryBox);
                                    container.getChildren().add(orderContainer);

                                    VBox yeni = new VBox();
                                    yeni.setPrefWidth(500.0);
                                    yeni.setPrefWidth(300.0);
                                    yeni.getStyleClass().add("yeni");
                                    container.getChildren().add(yeni);


                                    productContainer.setPrefHeight(500.0); // ürün scrollu ekrana yayılıyor.
                                    productContainer.setPrefWidth(300.0);
                                    soldProduct.setPrefHeight(300.0);

                                    selectedTableBarText.setText("Seçilen masa "+tablePane.getText());

                                    selectedTableBar.setVisible(true);
                                    hr.setVisible(true);
                                    productContainer.setVisible(true);
                                    soldProduct.setVisible(true);
                                    check.setVisible(true);
                                    closeTableOrders.setVisible(true);


                                    // Masa seçim ekranına dönülüyor.
                                    selectedTableBar.setOnMouseClicked(clicked -> {
                                        showTableChoosingScreen(tableRow);
                                        container.getChildren().removeAll(topBar);
                                        container.getChildren().add(0,topBar);
                                        container.getChildren().add(complaintContainer);
                                        container.getChildren().add(complaintButonContainer);
                                    });

                                    // Masa kapatılıp, masa seçim ekranına döndürülüyor.
                                    closeTableOrders.setOnMouseClicked(clicked -> {
                                        try {
                                            response = new Common().doHttpPost(Common.baseURL+"closeTable.php?tableID="+tablePane.getText());
                                            if (Objects.equals(String.valueOf(response).trim(),"done")){
                                                Common.createAlert("Masa başarıyla kapatıldı.").showAndWait();
                                                new Common().createLog(tablePane.getText()+" numaralı masa kapatıldı");

                                                showTableChoosingScreen(tableRow);
                                                container.getChildren().removeAll(topBar);
                                                container.getChildren().add(0,topBar);
                                            }
                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        }

                                    });

                                    // Yeni sipariş için ürünler çekiliyor.
                                    try {
                                        response = new Common().doHttpPost(Common.baseURL+"getProduct.php");
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }

                                    JsonArray productArr = new JsonParser().parse(String.valueOf(response)).getAsJsonArray(); // ürünler veritabananından çekildi.

                    /*
                        Sipariş ekranında kasiyer, ürün yelpazesi içinden ürünü seçmesi için bir bar yapıldı. Bu bara veriler dinamik bir şekilde
                        aşağıdaki for döngüsü ile bastırılıyor.
                    */
                                    for (int l = 0; l < productArr.size(); l++){ // sipariş için ürün seçim ekranı açılıyor.
                                        JsonObject productObj = productArr.get(l).getAsJsonObject();

                                        HBox productRow = new HBox(); // herbir ürünü satır halinde tutan taşıyıcı.
                                        productRow.setAlignment(Pos.CENTER);
                                        productRow.setMaxWidth(420);
                                        productRow.setMaxHeight(100);

                                        // Yeni sipariş için ürün seçilip, ekrana siparişi tamamlamak için ürün bilgilerini bastırma işlemi burada yapılıyor.
                                        productRow.setOnMouseClicked((MouseEvent event) -> {
                                            willBeSoldProduct.getChildren().removeAll(willBeSoldProduct.getChildren());

                                            TextField productName = new TextField();
                                            productName.setText(productObj.get("name").getAsString());
                                            productName.setEditable(false);
                                            TextField productCount = new TextField();
                                            productCount.setText("1");
                                            productCount.setId("countField");

                                            productCount.getStyleClass().add("urunlistele");
                                            productName.getStyleClass().add("urunlistele");

                                            willBeSoldProduct.setSpacing(5.0);

                                            javafx.scene.control.Button sendOrder = new javafx.scene.control.Button("Siparişlere Ekle");
                                            sendOrder.getStyleClass().add("siparisbuton");

                                            willBeSoldProduct.getChildren().addAll(productName,productCount,sendOrder); // Sipariş bilgileri onaylanmak üzere ekrana bastırıldı.

                                            // Seçilen ürün, sipariş olarak onaylandı, kaydı burada yapılıyor.
                                            sendOrder.setOnMouseClicked((MouseEvent order) -> {
                                                try {
                                                    response = new Common().doHttpPost(Common.baseURL+"makeOrder.php?productID="+productObj.get("productID").getAsString()
                                                            +"&tableID="+Controller.SELECTED_TABLE_NUMBER+"&count="+productCount.getText()+"&BranchCode=1");
                                                    if (Objects.equals(String.valueOf(response).trim(),"done")){
                                                        Common.createAlert(productObj.get("name").getAsString()+" başarıyla eklendi").showAndWait();

                                                        getAndFillAlreadyOrdered(); // siparişleri yenilemek için.

                                                    }else{
                                                        Common.createAlert("Err; "+response).showAndWait();
                                                    }
                                                } catch (IOException e1) {
                                                    e1.printStackTrace();
                                                }
                                            });

                                        });

                                        WebView ımageView = new WebView();
                                        WebEngine webEngine = ımageView.getEngine();
                                        webEngine.load(Common.baseURL+"images/"+productObj.get("productID").getAsString()+".png");
                                        ımageView.setPrefWidth(300);
                                        ımageView.setMaxHeight(200);
                                        productRow.getChildren().add(ımageView);


                                        Text productName = new Text(productObj.get("name").getAsString());
                                        productName.setWrappingWidth(200);
                                        productName.prefHeight(150);
                                        productName.setTextAlignment(TextAlignment.CENTER);
                                        productName.getStyleClass().add("urunmetin");
                                        productRow.getChildren().add(productName);

                                        productRow.setMaxHeight(200.0);

                                        productContainer.getChildren().add(productRow);

                                    }

                                    soldProductTitle.setText(tablePane.getText()+" numaralı masanın önceki siparişleri\n--------------");

                                    getAndFillAlreadyOrdered();

                                });

                /*
                    İçteki for işlemi her satır için numberOfTableInLayout kadar çalışıyor ve bu değişken sabit.
                    Eğer satır dolmadan masa biterse, içteki for döngüsü counterı da alarak olmayan masaları
                    çekmeye çalışıyor ve bu da outOfBoudIndex hatası veriyor.Aşağıdaki kontrolcü, counterı
                    kullanarak, çekilen masaların tamamlanıp tamamlanmadığını kontrol ediyor.
                */

                                if(tableNumber == numberOfTable){
                                    break;
                                }
                                tableNumber++;

                            }
                            tableContainer.getChildren().add(tableRow);
                        }

                    }else {
                        sceneContainer.prefHeightProperty().bind(scene.getWindow().heightProperty().add(-16));
                        sceneContainer.prefWidthProperty().bind(scene.getWindow().widthProperty().add(-150));

                    }

                    //userName.setText(Common.getEmployeeName()+"");

                    new Common().createLog(Common.getEmployeeName() +" sisteme giriş yaptı");


                    break;
                }
            }


        }else {
            Common.createAlert("Kullanıcı adı/Şifre yanlış").showAndWait();
        }
    }

    /* @TODO ***************   home.fxml   *************** */



    @FXML
    VBox leftBar;

    @FXML
    Button addproduct,updateproduct,addstatu,editcategory,openAddOrderButton,createShiftButon,updateTableButon;



    public void openAddNewProduct(MouseEvent mouseEvent) throws IOException {
        changeLayout("addNewProduct");

        addNewProductContainer.prefWidthProperty().bind(sceneContainer.widthProperty());
        addNewProductContainer.prefHeightProperty().bind(sceneContainer.heightProperty());
        addNewProductContainer.setPadding(new Insets(50,0,0,0));

        getAndFillCategory();
        category_list.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        category_list.getSelectionModel().selectFirst();
    }

    public void openUpdateNewProduct(MouseEvent mouseEvent) throws IOException {

        changeLayout("updateNewProduct");

        updateNewProductContainer.prefWidthProperty().bind(sceneContainer.widthProperty());
        table.prefWidthProperty().bind(sceneContainer.widthProperty());

        categoryIDCol.setCellValueFactory(new PropertyValueFactory<Product,Integer>("categoryName"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));
        costPriceCol.setCellValueFactory(new PropertyValueFactory<Product,Integer>("costPrice"));
        salePriceCol.setCellValueFactory(new PropertyValueFactory<Product,Integer>("salePrice"));


        getAndFillTable();

        table.setOnMouseClicked((MouseEvent a) -> {
            if (a.getClickCount() > 1){
                if(table.getSelectionModel().getSelectedItem() != null){

                    alreadySelected = table.getSelectionModel().getSelectedItem();
                    try {
                        getAndFillCategory();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    name.setText(alreadySelected.getName());
                    cost.setText(String.valueOf(alreadySelected.getCostPrice()));
                    sale.setText(String.valueOf(alreadySelected.getSalePrice()));
                    category_list.getSelectionModel().select(alreadySelected.getCategoryID()-1);
                    updateLayout.setVisible(true);
                }
            }
        });

    }

    public void openAddOrder(MouseEvent mouseEvent) throws IOException{
        changeLayout("addOrder");


        container.prefWidthProperty().bind(sceneContainer.widthProperty());
        container.prefHeightProperty().bind(sceneContainer.heightProperty());
        orderContainer.prefWidthProperty().bind(sceneContainer.widthProperty());
        orderContainer.prefHeightProperty().bind(sceneContainer.heightProperty());
        productContainer.prefHeightProperty().bind(sceneContainer.heightProperty());
        productContainer.setMinWidth(200);
        soldProductContainer.prefWidthProperty().bind(orderContainer.widthProperty().subtract(productContainer.widthProperty()));

        container.getChildren().removeAll(orderContainer);

        response = new Common().doHttpPost(Common.baseURL+"getTableCount.php");

        sceneContainer.layout(); // getWidth fonksiyonunun 0 döndürmemesi için gerekli!

        System.out.println("Layoutun toplam genişliği: "+tableContainer.getWidth());
        System.out.println("Masa sayısı: "+Integer.parseInt(String.valueOf(response).trim()));



        int numberOfTable = Integer.parseInt(String.valueOf(response).trim());
        int tableNumber = 1;
        int numberOfTableInLayout = (int) (tableContainer.getWidth()/100);
        // Masa sayısı 13, satırdaki masa sayısı 7 olursa satır sayısı 2 çıkması gerekiyorken 1 çıkıyor.
        // Java 2. satırı, işlemi aşağı yuvarladığı için yok ediyor. Bu yüzden işlemin sonucunu yukarı yuvarladım.
        // Ayrıca işlemin sonucunun yuvarlanabilir olması için int değil double kullandım.
        int numberOfRow = (int) Math.ceil((double) numberOfTable/(double) numberOfTableInLayout);
        System.out.println("Bir satırdaki masa sayısı: "+numberOfTableInLayout+"\nSatır sayısı: "+numberOfRow);

        for (int i = 0; i < numberOfRow; i++){

            HBox tableRow = new HBox(); // Masaları tutan taşıyıcı.
            tableRow.setPrefHeight(100);
            tableRow.setMaxWidth(numberOfTableInLayout*100);
            tableRow.setId("row"+i);

            for (int j = 0; j < numberOfTableInLayout; j++){
                javafx.scene.control.Button tablePane = new javafx.scene.control.Button();
                tablePane.setId("tableButton");
                tablePane.setText(tableNumber+""); // Masa numaraları burada bastırılıyor.
                tablePane.setAlignment(Pos.CENTER);
                tablePane.setPrefWidth(100);
                tablePane.setPrefHeight(100);
                tableRow.getChildren().add(tablePane);

                // bir masa seçildi.
                tablePane.setOnMouseClicked((MouseEvent e) -> {
                    Controller.SELECTED_TABLE_NUMBER = Integer.parseInt(tablePane.getText());
                    productContainer.getChildren().removeAll(productContainer.getChildren()); // Tekrar masa seçimi yapıldığında tekrarlanan veri olmaması için

                    /*
                        Aşağıdaki kod satırı ile istediğim zaman masa şemasını aynı yere koyabiliyorum.
                        container.getChildren().add(1,tableContainer);

                        Tekrar masa seçim ekranına dönüldüğünde aşağıdaki işlemlerin tam tersi yapılacak.
                    */
                    container.getChildren().remove(tableContainer); // masa sahnesi ekrandan kaldırılıyor.
                    container.getChildren().add(orderContainer);

                    soldProduct.setPrefHeight(300.0);
                    selectedTableBar.setVisible(true);
                    selectedTableBarText.setText("Seçilen masa "+tablePane.getText());

                    // Masa seçim ekranına dönülüyor.
                    selectedTableBar.setOnMouseClicked(clicked -> {
                        showTableChoosingScreen(tableRow);
                    });

                    // Masa kapatılıp, masa seçim ekranına döndürülüyor.
                    closeTableOrders.setOnMouseClicked(clicked -> {
                        try {
                            response = new Common().doHttpPost(Common.baseURL+"closeTable.php?tableID="+tablePane.getText());
                            if (Objects.equals(String.valueOf(response).trim(),"done")){
                                Common.createAlert("Masa başarıyla kapatıldı.").showAndWait();
                                new Common().createLog(tablePane.getText()+" numaralı masa kapatıldı");

                                showTableChoosingScreen(tableRow);
                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                    });

                    // Yeni sipariş için ürünler çekiliyor.
                    try {
                        response = new Common().doHttpPost(Common.baseURL+"getProduct.php");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    JsonArray productArr = new JsonParser().parse(String.valueOf(response)).getAsJsonArray(); // ürünler veritabananından çekildi.

                    /*
                        Sipariş ekranında kasiyer, ürün yelpazesi içinden ürünü seçmesi için bir bar yapıldı. Bu bara veriler dinamik bir şekilde
                        aşağıdaki for döngüsü ile bastırılıyor.
                    */
                    for (int l = 0; l < productArr.size(); l++){ // sipariş için ürün seçim ekranı açılıyor.
                        JsonObject productObj = productArr.get(l).getAsJsonObject();

                        HBox productRow = new HBox(); // herbir ürünü satır halinde tutan taşıyıcı.
                        productRow.setAlignment(Pos.CENTER);
                        productRow.setMaxWidth(200);
                        productRow.setPrefHeight(100);

                        // Yeni sipariş için ürün seçilip, ekrana siparişi tamamlamak için ürün bilgilerini bastırma işlemi burada yapılıyor.
                        productRow.setOnMouseClicked((MouseEvent event) -> {
                            willBeSoldProduct.getChildren().removeAll(willBeSoldProduct.getChildren());

                            TextField productName = new TextField();
                            productName.setText(productObj.get("name").getAsString());
                            productName.setEditable(false);
                            TextField productCount = new TextField();
                            productCount.setText("1");
                            productCount.setId("countField");

                            willBeSoldProduct.setSpacing(5.0);

                            javafx.scene.control.Button sendOrder = new javafx.scene.control.Button("Siparişlere Ekle");

                            willBeSoldProduct.getChildren().addAll(productName,productCount,sendOrder); // Sipariş bilgileri onaylanmak üzere ekrana bastırıldı.

                            // Seçilen ürün, sipariş olarak onaylandı, kaydı burada yapılıyor.
                            sendOrder.setOnMouseClicked((MouseEvent order) -> {
                                try {
                                    response = new Common().doHttpPost(Common.baseURL+"makeOrder.php?productID="+productObj.get("productID").getAsString()
                                    +"&tableID="+Controller.SELECTED_TABLE_NUMBER+"&count="+productCount.getText());
                                    if (Objects.equals(String.valueOf(response).trim(),"done")){
                                        Common.createAlert(productObj.get("name").getAsString()+" başarıyla eklendi").showAndWait();

                                    getAndFillAlreadyOrdered(); // siparişleri yenilemek için.

                                    }else{
                                        Common.createAlert("Err; "+response).showAndWait();
                                    }
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            });

                        });

                        WebView ımageView = new WebView();
                        WebEngine webEngine = ımageView.getEngine();
                        webEngine.load(Common.baseURL+"images/"+productObj.get("productID").getAsString()+".png");
                        ımageView.setPrefWidth(100);
                        ımageView.setPrefHeight(100);
                        productRow.getChildren().add(ımageView);


                        Text productName = new Text(productObj.get("name").getAsString());
                        productName.setWrappingWidth(100);
                        productName.prefHeight(100);
                        productName.setTextAlignment(TextAlignment.CENTER);
                        productRow.getChildren().add(productName);

                        productContainer.getChildren().add(productRow);

                    }

                    soldProductTitle.setText(tablePane.getText()+" numaralı masanın önceki siparişleri\n--------------");

                    getAndFillAlreadyOrdered();

                });

                /*
                    İçteki for işlemi her satır için numberOfTableInLayout kadar çalışıyor ve bu değişken sabit.
                    Eğer satır dolmadan masa biterse, içteki for döngüsü counterı da alarak olmayan masaları
                    çekmeye çalışıyor ve bu da outOfBoudIndex hatası veriyor.Aşağıdaki kontrolcü, counterı
                    kullanarak, çekilen masaların tamamlanıp tamamlanmadığını kontrol ediyor.
                */

                if(tableNumber == numberOfTable){
                    break;
                }
                tableNumber++;

            }
            tableContainer.getChildren().add(tableRow);
        }


    }

    public void openSoldProduct(MouseEvent mouseEvent) throws IOException{
        changeLayout("soldProductt");

        soldProductContainerr.prefWidthProperty().bind(sceneContainer.widthProperty());
        soldProductContainerr.prefHeightProperty().bind(sceneContainer.heightProperty());
        branchChoise.getItems().add("Tüm Şubeler");
        response = new Common().doHttpPost(Common.baseURL+"getBranch.php");
        JsonArray BranchArr = new JsonParser().parse(String.valueOf(response)).getAsJsonArray();
        for (int i = 0; i < BranchArr.size(); i++){
            JsonObject statuObj = BranchArr.get(i).getAsJsonObject();
            String BranchName = statuObj.get("BranchName").getAsString();
            branchChoise.getItems().add(BranchName);
        }
        branchChoise.getSelectionModel().selectFirst();
     /*  productIDCol.setCellValueFactory(new PropertyValueFactory<SoldProduct, Integer>("productID"));
        categoryIDCol.setCellValueFactory(new PropertyValueFactory<SoldProduct,Integer>("categoryID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<SoldProduct,String>("name"));
        profitCol.setCellValueFactory(new PropertyValueFactory<SoldProduct,Double>("profit"));
        countCol.setCellValueFactory(new PropertyValueFactory<SoldProduct,Double>("count"));

        response = new Common().doHttpPost(Common.baseURL+"getSoldProduct.php");

        JsonArray soldProductArr = new JsonParser().parse(String.valueOf(response)).getAsJsonArray();
        for (int i = 0; i < soldProductArr.size(); i++){
            JsonObject soldProductObj = soldProductArr.get(i).getAsJsonObject();
            int productID = soldProductObj.get("productID").getAsInt();
            int categoryID = soldProductObj.get("categoryID").getAsInt();
            String name = soldProductObj.get("name").getAsString();
            double sale = soldProductObj.get("sale").getAsDouble();
            double cost = soldProductObj.get("cost").getAsDouble();
            int count = soldProductObj.get("count").getAsInt();
            double profit = (sale-cost)*count;
            soldProductTable.getItems().add(new SoldProduct(productID,categoryID,name,profit,count));
        }*/
    }

    public void openEmployee(MouseEvent mouseEvent) throws IOException{
        changeLayout("employee");

        employeeContainer.prefWidthProperty().bind(sceneContainer.widthProperty());
        employeeContainer.prefHeightProperty().bind(sceneContainer.heightProperty());

        employeeTcNoCol.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("employeeTcNo"));
        employeeNameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeName"));
        employeePhoneCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeePhone"));
        employeeAddressCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeAddress"));
        statuIDCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("statuName"));


        getAndFillEmployee();
    }

    public void openStatu(MouseEvent mouseEvent) throws  IOException{
        changeLayout("statu");

        statuContainer.prefWidthProperty().bind(sceneContainer.widthProperty());
        statuContainer.prefHeightProperty().bind(sceneContainer.heightProperty());

        statuIDCol.setCellValueFactory(new PropertyValueFactory<Statu, Integer>("statuID"));
        statuNameCol.setCellValueFactory(new PropertyValueFactory<Statu, String>("statuName"));

        getAndFillStatu();

    }
    public void openShift(MouseEvent mouseEvent) throws IOException {
        changeLayout("shift");

        shiftCont.prefWidthProperty().bind(sceneContainer.widthProperty());
        shiftCont.prefHeightProperty().bind(sceneContainer.heightProperty());


        HBox addGroupContainer = new HBox();
        addGroupContainer.setPadding(new Insets(10,10,10,10));
        addGroupContainer.setStyle("-fx-background-color: red");

        Text groupContainerTitle = new Text("Yeni grup ekle!");
        groupContainerTitle.setWrappingWidth((sceneContainer.getWidth()/2)-10);
        Text addGroupButton = new Text("[Add New Group]");
        addGroupButton.setWrappingWidth((sceneContainer.getWidth()/2)-10);
        addGroupButton.setTextAlignment(TextAlignment.RIGHT);

        addGroupContainer.getChildren().addAll(groupContainerTitle,addGroupButton);
        shiftContainer.getChildren().add(addGroupContainer);
        /********  Grup ekleme barı eklendi. */


        final int[] groupNoCounter = {1};  // Ekrana bastırılan vardiya gruplarının numaralarının tutuyor. ( groupNo[0] )


        /* Daha önceden kaydedilmiş gruplar veritabaından çekiliyor. */
        response = new Common().doHttpPost(Common.baseURL+"getShiftGroup.php");
        JsonArray shiftGroupArr = new JsonParser().parse(String.valueOf(response)).getAsJsonArray();
        for (int i = 0; i < shiftGroupArr.size(); i++){ // internetten çekilen grupları bastıran döngü
            JsonObject shiftGroupObj = shiftGroupArr.get(i).getAsJsonObject();
            groupNoCounter[0] = shiftGroupObj.get("groupNo").getAsInt();

            VBox employeeOfAboveGroup = createShiftGroup(groupNoCounter,true); // Gruplar ve o gruba ait çalışanlar veritabanından çekilip bastırılıyor.
            fillShiftGroup(shiftGroupObj,employeeOfAboveGroup,groupNoCounter[0]);

            groupNoCounter[0]++;

        }

        addGroupContainer.setOnMouseClicked((MouseEvent clicked) -> {

            try {
                createShiftGroup(groupNoCounter,false); // Yeni grup oluşturuluyor. Gruba çalışan eklenip ekrana bastırılıyor.
            } catch (IOException e) {
                e.printStackTrace();
            }
            groupNoCounter[0]++;

        });



    }

    public void openPrepareShift(MouseEvent mouseEvent) throws IOException{
        changeLayout("prepareShift");

        prepareShiftCont.prefWidthProperty().bind(sceneContainer.widthProperty());
        prepareShiftCont.prefHeightProperty().bind(sceneContainer.heightProperty());
        prepareShiftContainer.prefHeightProperty().bind(sceneContainer.heightProperty());

        // Vardiyayı onaylayıp kaydetmek için aşağıdaki metrikler kullanılıyor.
        // Bu metrikler her sayfaya girildiğinde aşağıdaki 2 satır ile default değerine çekiliyor.
        numberOfShiftDay = 0;
        numberOfAcceptedGroup = 0;
        for (int i=0 ; i < shiftDayArrayList.size(); i++)
         shiftDayArrayList.remove(shiftDayArrayList.get(i));

        HBox addNewDay = new HBox();
            addNewDay.setAlignment(Pos.CENTER_LEFT);
            addNewDay.setPadding(new Insets(10,10,10,10));
            addNewDay.setSpacing(10.0);
        prepareShiftContainer.getChildren().add(addNewDay);

        TextField day = new TextField();
            day.setPromptText("dd");
            day.setMaxWidth(40.0);
        TextField month = new TextField();
            month.setPromptText("mm");
            month.setMaxWidth(40.0);
        TextField year = new TextField();
            year.setPromptText("yyyy");
            year.setMaxWidth(50.0);

        Text divider = new Text("/");
        Text divider2 = new Text("/");
        addNewDay.getChildren().addAll(day,divider,month,divider2,year);

        VBox space = new VBox();
        space.setPrefWidth(50.0);


        TextField groupsInDay = new TextField();
        groupsInDay.setPromptText("Günlük çalışacak grup sayısı");
        groupsInDay.setFocusTraversable(false);

        Button addNewDayButton = new Button("Onayla!");
        addNewDayButton.setStyle("-fx-background-color: cornflowerblue; -fx-text-fill: white");


        addNewDay.getChildren().addAll(groupsInDay,space,addNewDayButton);

        //---- Yeni bir gün ekleme layoutu ekrana bastırıldı.


        final int[] counter = {1};
        addNewDayButton.setOnMouseClicked(clicked -> { // Vardiya için yeni bir gün ekleniyor.
            numberOfAcceptedGroup = Integer.parseInt(groupsInDay.getText());
            numberOfShiftDay += 1;

            HBox dayContainer = new HBox();
                dayContainer.setId(day.getText()+"/"+month.getText()+"/"+year.getText());
                dayContainer.setPadding(new Insets(20,20,20,20));
                dayContainer.setAlignment(Pos.CENTER_LEFT);
            prepareShiftContainer.getChildren().add(dayContainer);

            Text theDay = new Text(counter[0] +". gün "+dayContainer.getId());
            dayContainer.getChildren().add(theDay);


            //-- Tarih texti eklendi


            VBox groupAndhoursContainer = new VBox();
            groupAndhoursContainer.setPadding(new Insets(10,10,10,10));
            dayContainer.getChildren().addAll(groupAndhoursContainer);


            // groupAndhoursContainer'ın içi dolduruluyor. Database'den kayıtlı gruplar çekiliyor.
            for (int i = 1; i <= Integer.parseInt(groupsInDay.getText());i++){
                HBox anyGroup = new HBox();
                    anyGroup.setAlignment(Pos.CENTER);

               ChoiceBox groupNoChoiseBox = new ChoiceBox();

                try {
                    response = new Common().doHttpPost(Common.baseURL+"getGroups.php");
                    JsonArray groupArr = new JsonParser().parse(String.valueOf(response)).getAsJsonArray();
                    for (int n = 0; n < groupArr.size(); n++){
                        JsonObject groupObj = groupArr.get(n).getAsJsonObject();
                        int groupNo = groupObj.get("groupNo").getAsInt();
                        System.out.println("group "+groupNo);
                        groupNoChoiseBox.getItems().add("Group "+groupNo);
                        groupNoChoiseBox.getSelectionModel().selectFirst();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // ---- Grupları çeken choisebox ekrana bastırılıyor.

                TextField start = new TextField();
                    start.setPromptText("00:00");
                    start.setFocusTraversable(false);
                Text startFinishDivider = new Text("-");
                TextField finish = new TextField();
                    finish.setPromptText("00:00");
                    finish.setFocusTraversable(false);

                VBox spaceBox = new VBox();
                spaceBox.setPrefWidth(30.0);

                Button accepShiftDay = new Button();
                accepShiftDay.setText("Onayla!");
                accepShiftDay.setStyle("-fx-background-color: cornflowerblue; -fx-text-fill: white");

                VBox spaceBox2 = new VBox();
                spaceBox2.setPrefWidth(30.0);

                anyGroup.getChildren().addAll(groupNoChoiseBox,spaceBox,start,startFinishDivider,finish,spaceBox2,accepShiftDay);
                groupAndhoursContainer.getChildren().add(anyGroup);
                // ------ Çalışma aralıklarını belirten layoutlar eklendi.


                accepShiftDay.setOnMouseClicked(addShiftDat -> {
                    String shiftDay = dayContainer.getId();
                    int groupNo = groupNoChoiseBox.getSelectionModel().getSelectedIndex()+1;

                    System.out.println("Eski size: "+shiftDayArrayList.size());
                    shiftDayArrayList.add(new ShiftDay(shiftDay,groupNo,start.getText()+"-"+finish.getText()));
                    System.out.println("Yeni size: "+shiftDayArrayList.size());

                    accepShiftDay.setStyle("-fx-background-color: #E0E0E0;-fx-text-fill: black");
                    accepShiftDay.setText("Onaylandı");
                    accepShiftDay.setDisable(true);
                    Common.createAlert(shiftDay+" tarihinde "+groupNo+" numaralı grubun vardiyası onaylandı!").showAndWait();
                });

            }

            counter[0]++;
        });


    }

    public void openEditCategory(MouseEvent mouseEvent) throws  IOException{

        changeLayout("editCategory");

        editCategoryContainer.prefWidthProperty().bind(sceneContainer.widthProperty());
        editCategoryContainer.prefHeightProperty().bind(sceneContainer.heightProperty());

        categoryIDCol.setCellValueFactory(new PropertyValueFactory<EditCategory, Integer>("categoryID"));
        categoryNameCol.setCellValueFactory(new PropertyValueFactory<EditCategory, String>("categoryName"));

        getAndFillCategoryForEdit();

        editCategoryTableView.setOnMouseClicked(selected -> {
            if (selected.getClickCount() > 1 ){
                if (editCategoryTableView.getSelectionModel().getSelectedItem() != null){
                    category = editCategoryTableView.getSelectionModel().getSelectedItem();
                    categoryUpdateText.setText(category.getCategoryName());
                }
            }
        });

    }

    public void openUpdateTableCount(MouseEvent mouseEvent) throws  IOException{

        changeLayout("updateTableCount");

        updateTableCountContainer.prefWidthProperty().bind(sceneContainer.widthProperty());
        updateTableCountContainer.prefHeightProperty().bind(sceneContainer.heightProperty());

        response = new Common().doHttpPost(Common.baseURL+"getTableCount.php");
        final int[] tableCountPrompText = {Integer.parseInt(String.valueOf(response).trim())};

        HBox contaniner = new HBox();
            contaniner.setAlignment(Pos.CENTER);
            contaniner.setSpacing(10.0);
        TextField tableCount = new TextField();
            tableCount.setPromptText(tableCountPrompText[0]+"");
        Button updateTableCount = new Button("Onayla!");
            updateTableCount.setStyle("-fx-background-color: blue; -fx-text-fill: white");
        contaniner.getChildren().addAll(tableCount,updateTableCount);
        updateTableCountContainer.getChildren().add(contaniner);

        updateTableCount.setOnMouseClicked(updateButton -> {

            if (tableCount.getText().isEmpty()){
                Common.createAlert("Boş geçilemez!").showAndWait();
                return;
            }


            if ( Integer.parseInt(tableCount.getText()) == 0){
                Common.createAlert("Masa sayısı 0 olamaz!").showAndWait();
                return;
            }else {
                try {
                    response = new Common().doHttpPost(Common.baseURL+"updateTableCount.php?tableCount="+tableCount.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Objects.equals(String.valueOf(response),"done")){
                    Common.createAlert("İşletmedeki masa sayısı "+tableCount.getText()+" olarak güncellendi!").showAndWait();
                    tableCount.setText("");
                    try {
                        response = new Common().doHttpPost(Common.baseURL+"getTableCount.php");
                        new Common().createLog("İşletmedeki masa sayısı "+response+" olarak güncellendi");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    tableCountPrompText[0] = Integer.parseInt(String.valueOf(response).trim());
                    tableCount.setPromptText(tableCountPrompText[0]+"");
                }else {
                    Common.createAlert("Err; "+response).showAndWait();
                }

            }
        });
    }

    public void openEditBranch(MouseEvent mouseEvent)throws IOException{

        changeLayout("EditBranch");



        CodeCol.setCellValueFactory(new PropertyValueFactory<Branch,Integer>("BranchCode"));
        NameCol.setCellValueFactory(new PropertyValueFactory<Branch,String>("BranchName"));
        AdressCol.setCellValueFactory(new PropertyValueFactory<Branch,String>("BranchAdress"));
        PhoneCol.setCellValueFactory(new PropertyValueFactory<Branch,String>("BranchPhone"));


        try{
            response = new Common().doHttpPost(Common.baseURL + "getBranch.php");

        }catch(IOException e){
            e.printStackTrace();
        }

        JsonArray BranchArr = new JsonParser().parse(String.valueOf(response)).getAsJsonArray();

        int code = 0;String name = "a";String adress = "b";String phone = "c";
        for (int i = 0; i < BranchArr.size(); i++) {
            JsonObject BranchObj = BranchArr.get(i).getAsJsonObject();

             code = BranchObj.get("BranchCode").getAsInt();
             name  = BranchObj.get("BranchName").getAsString();
             adress  = BranchObj.get("BranchAdress").getAsString();
             phone = BranchObj.get("BranchPhone").getAsString();

            System.out.println(String.valueOf(code) + name + adress + phone);
            Branch sube = new Branch(code,name,adress,phone);
            System.out.println(sube.getBranchAdress() + sube.getBranchName());
            branchTable.getItems().add(sube);
        }
    }

    public void logOut(MouseEvent mouseEvent) throws IOException {

        Stage primaryStage = (Stage) scene.getScene().getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Giriş Yap!");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());

        new Common().createLog(Common.getEmployeeName() +" sistemden çıkış yaptı");


    }

    public void openComplaint(MouseEvent mouseEvent)throws IOException{
        changeLayout("showComplaint");
        String[] deneme = {"Tüm Şubeler","Konyaaltı Şubesi","Güllük Şubesi","Lara Şubesi"};
        brChoise.getItems().addAll(deneme);

        Complaint cmp1 = new Complaint("25.07.2017","Servis hizmeti geç işliyor", "Konyaaltı Şubesi");
        Complaint cmp2 = new Complaint("25.07.2017","Sulu yemekler lezzetli değil", "Lara Şubesi");
        Complaint cmp3 = new Complaint("27.07.2017","Havalandırma yetersiz", "Güllük Şubesi");
        Complaint cmp4 = new Complaint("29.07.2017","Personel yeteri kadar özenli hizmer vermiyor.", "Konyaaltı Şubesi");
        Complaint cmp5 = new Complaint("30.07.2017","Izgaralar daha fazla pişirilse daha iyi olur.", "Lara Şubesi");

        Complaint cmp6 = new Complaint("01.08.2017","Soğutma sistemi daha iyi olmalı", "Konyaaltı Şubesi");
        Complaint cmp7 = new Complaint("01.08.2017","Çorbalar daha sıcak olacak şekilde servis edilmeli", "Güllük Şubesi");
        Complaint cmp8 = new Complaint("02.08.2017","Soğuk içecek çeşidi yeterli değil", "Lara Şubesi");
        Complaint cmp9 = new Complaint("03.08.2017","Hizmet kalitesi arttırılmalı", "Konyaaltı Şubesi");
        Complaint cmp10 = new Complaint("05.08.2017","Menü biraz daha ekonomik olmalı", "Güllük Şubesi");
        Complaint cmp11 = new Complaint("06.08.2017","Menüdeki çeşitler arttırılmalı", "Konyaaltı Şubesi");

        dateCol.setCellValueFactory(new PropertyValueFactory<Complaint,String>("tarih"));
        expCol.setCellValueFactory(new PropertyValueFactory<Complaint,String>("aciklama"));
        brCol.setCellValueFactory(new PropertyValueFactory<Complaint,String>("Sube"));

        complaintTable.getItems().addAll(cmp1,cmp2,cmp3,cmp4,cmp5,cmp6,cmp7,cmp8,cmp9,cmp10,cmp11);

    }

    public void showLogRecord (MouseEvent mouseEvent)throws IOException {
        changeLayout("showLogRecords");
        String subeliste[] = {"Konyaaltı Şubesi", "Güllük Şubesi", "Lara Şubesi"};
        logbrChoise.getItems().addAll(subeliste);

        LogRecords rc1 = new LogRecords("24145678985", "Ahmet DEMİR", "Kasiyer", "Kullanıcı sisteme giriş yaptı", "27/07/2017 12:35:21");
        LogRecords rc2 = new LogRecords("24145678985", "Ahmet DEMİR", "Kasiyer", "14 numaralı masa için 76.5 TL tutarında sipariş oluşturuldu", "27/07/2017 12:35:39");
        LogRecords rc3 = new LogRecords("24145678985", "Ahmet DEMİR","Kasiyer","5 numaralı masam için 45.0 TL tutarında sipariş oluşturuldu","27/07/2017 12:36:04");
        LogRecords rc4 = new LogRecords("24145678985","Ahmet DEMİR","Kasiyer","Kullanıcı sistemden çıkış yaptı","27/07/2017 12:36:17");
        LogRecords rc5 = new LogRecords("25639188463","Emrah Efe AKSOY","Şube Müdürü","Kullanıcı sisteme giriş yaptı","29/07/2017 17:23:54");
        LogRecords rc6 = new LogRecords("25639188463","Emrah Efe AKSOY","Şube Müdürü","Şubedeki masa sayısı 22 olarak güncellendi","29/07/2017 17:24:02");
        LogRecords rc7 = new LogRecords("25639188463","Emrah Efe AKSOY","Şube Müdürü","Yani vardiya sistemi oluşturuldu","29/07/2017 17:25:42");
        LogRecords rc8 = new LogRecords("25639188463","Emrah Efe AKSOY","Şube Müdürü","Kullanıcı sistemden çıkış yaptı","29/07/2017 17:26:13");
        LogRecords rc9 = new LogRecords("21784596125","Selim MUTLU","Kasiyer","Kullanıcı sisteme giriş yaptı","29/07/2017 17:27:02");
        LogRecords rc10 = new LogRecords("21784596125","Selim MUTLU","Kasiyer","22 numaralı masa için 107.0 TL tutarında sipariş oluşturuldu","29/07/2017 17:27:49");
        LogRecords rc11 = new LogRecords("21784596125","Selim MUTLU","Kasiyer","Kullanıcı sistemden çıkış yaptı","29/07/2017 17:27:55");

        userNameCol.setCellValueFactory(new PropertyValueFactory<LogRecords,String>("userName"));
        userTcNoCol.setCellValueFactory(new PropertyValueFactory<LogRecords,String>("userTcNo"));
        processCol.setCellValueFactory(new PropertyValueFactory<LogRecords,String >("process"));
        processTimeCol.setCellValueFactory(new PropertyValueFactory<LogRecords,String >("processTime"));
        userStatuCol.setCellValueFactory(new PropertyValueFactory<LogRecords,String>("userStatu"));

        LogTable.getItems().addAll(rc1,rc2,rc3,rc4,rc5,rc6,rc7,rc8,rc9,rc10,rc11);
    }







    /* @TODO ***************   createShift.fxml   *************** */
   @FXML
        VBox shiftEmployeeContainer;
   @FXML
        DatePicker baslangictarih,bitistarih;

    ArrayList<Integer> selectedEmployeeId = new ArrayList<Integer>();



    public void accept(MouseEvent mouseEvent) throws IOException{
     // try{
          LocalDate baslangic = baslangictarih.getValue();
          LocalDate bitis = bitistarih.getValue();

          int  gunfarki = (int) ChronoUnit.DAYS.between(baslangic,bitis);

        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d/MM/uuuu");


          for(int i = 0;i <= gunfarki;i++){
              String deneme = baslangic.format(formatters);
              System.out.println(deneme);
              baslangic = baslangic.plusDays(1);

          }

     // }catch (IOException e){

      //}
    }
    public void createShift(MouseEvent mouseEvent) throws IOException{
        changeLayout("createShift");
    }
    public void selectEmployee(MouseEvent mouseEvent) throws IOException{
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root,300,300);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Vardiya Grubu İçin Çalışan Seç");
        stage.show();

        TableView<Employee> employeeTableView = new TableView<>();
        TableColumn employeeNameCol = new TableColumn("Çalışan İsmi");
        employeeNameCol.setPrefWidth(280);
        employeeTableView.getColumns().add(employeeNameCol);
        employeeNameCol.setText("Seçilen çalışan 0");
        TableColumn employeeIDCol = new TableColumn("ID");
        employeeIDCol.setPrefWidth(20);
        employeeTableView.getColumns().add(employeeIDCol);
        employeeIDCol.setText("ID");
        employeeNameCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("employeeName"));
        employeeIDCol.setCellValueFactory(new PropertyValueFactory<Employee,Integer>("employeeID"));
        employeeTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        root.getChildren().add(employeeTableView);
        // çalışan tablosu eklendi.




        Button acceptButton = new Button();
        acceptButton.setStyle("-fx-background-color: cornflowerblue; -fx-text-fill: white");
        acceptButton.setText("Onayla!");

        root.getChildren().add(acceptButton);
        // Seçimi onaylama butonu eklendi.

        employeeTableView.getItems().removeAll(employeeTableView.getItems()); // tablo resetleniyor.

        try {
            response = new Common().doHttpPost(Common.baseURL+"getEmployee.php");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Çalışanlar : "+response);
        JsonArray employeeArr = new JsonParser().parse(String.valueOf(response)).getAsJsonArray();

        if(selectedEmployeeId==null) {
            for (int i = 0; i < employeeArr.size(); i++) {
                JsonObject employeeObj = employeeArr.get(i).getAsJsonObject();

                String employeeName = employeeObj.get("employeeName").getAsString();
                int employeeID = employeeObj.get("employeeID").getAsInt();

                employeeTableView.getItems().add(new Employee(employeeName, employeeID));
            }
        }
        else{

            for (int i = 0; i < employeeArr.size(); i++) {
                JsonObject employeeObj = employeeArr.get(i).getAsJsonObject();

                int employeeID = employeeObj.get("employeeID").getAsInt();

                if(!selectedEmployeeId.contains(employeeID)) {
                    String employeeName = employeeObj.get("employeeName").getAsString();
                    employeeTableView.getItems().add(new Employee(employeeName,employeeID));
                }

            }



        }

        acceptButton.setOnMouseClicked(accepted -> { // Vardiya grubuna çalışan ekleyen fonksiyon.
            if (employeeTableView.getSelectionModel().getSelectedItems() != null){
                List<Employee> selectedEmployee = employeeTableView.getSelectionModel().getSelectedItems();
                stage.close();

                for (int deger = 0; deger < selectedEmployee.size(); deger ++){
                    Employee calisan = selectedEmployee.get(deger);
                    selectedEmployeeId.add(calisan.getEmployeeID());
                    Text name = new Text();
                    name.setText("ID = " +  calisan.getEmployeeID() + " " +calisan.getEmployeeName() + " (Silmek için tıklayın)");
                    name.setOnMouseClicked(clicked -> {
                      shiftEmployeeContainer.getChildren().remove(name);
                      System.out.println(calisan.getEmployeeName());
                      selectedEmployeeId.remove((Object)calisan.getEmployeeID());


                    });



                    shiftEmployeeContainer.getChildren().add(name);


                }
            }else {
                Common.createAlert("En az bir çalışan seçilmeli!").showAndWait();
            }
        });





    }



    /* @TODO ***************   addNewProduct.fxml   *************** */
    @FXML
    TextField name, cost, sale; // ayrıca updateNewProduct sayfasında kullanılıyor.
    @FXML
    ChoiceBox<String> category_list; // ayrıca updateNewProduct sayfasında kullanılıyor.
    @FXML
    FileChooser fileChooser; // ayrıca updateNewProduct sayfasında kullanılıyor.
    @FXML
    File file; // ayrıca updateNewProduct sayfasında kullanılıyor.

    @FXML
    VBox addNewProductContainer;

    @FXML
    ChoiceBox<Employee> deneme;

    public void addNewProduct(MouseEvent mouseEvent) throws IOException{

        String nameText = name.getText();
        String costText = cost.getText();
        String saleText = sale.getText();
        String categoryText = category_list.getValue();
        int categoryID = category_list.getSelectionModel().getSelectedIndex()+1;

        if(file == null){
            Common.createAlert("Resim seçmeden kayıt yapamazsın!").showAndWait();
            return;
        }

        String charset = "UTF-8";

        String boundary = Long.toHexString(System.currentTimeMillis());
        String CRLF = "\r\n";
        HttpURLConnection connection = (HttpURLConnection) (new URL(Common.baseURL+"addProduct.php")).openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        try (
                OutputStream output = connection.getOutputStream();
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
        ) {


            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"name\"").append(CRLF);
            writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
            writer.append(CRLF).append(nameText).append(CRLF).flush();


            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"cost\"").append(CRLF);
            writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
            writer.append(CRLF).append(costText).append(CRLF).flush();


            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"sale\"").append(CRLF);
            writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
            writer.append(CRLF).append(saleText).append(CRLF).flush();


            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"categoryID\"").append(CRLF);
            writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
            writer.append(CRLF).append(categoryID+"").append(CRLF).flush();


            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"image\"; filename=\"" + file.getName() + "\"").append(CRLF);
            writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(file.getName())).append(CRLF);
            writer.append("Content-Transfer-Encoding: binary").append(CRLF);
            writer.append(CRLF).flush();
            Files.copy(file.toPath(), output);
            output.flush();
            writer.append(CRLF).flush();

            writer.append("--" + boundary + "--").append(CRLF).flush();

        }finally {
            StringBuffer response = null;
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                String inputLine;
                response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();
            }
            System.out.println(connection.getResponseCode()+"Incoming message from addProduct => "+response);
            Common.createAlert("Ekleme Başarıyla Tamamlandı!<br>" +
                    "İsim:"+nameText+"<br>"+
                    "Maliyeti:"+costText+"<br>"+
                    "Satış fiyatı:"+saleText+"<br>"+
                    "Kategorisi:"+categoryText+" & "+categoryID).showAndWait();
        }

        new Common().createLog(nameText+" ürünü sisteme eklendi");

    }

    public void addNewCategory(MouseEvent mouseEvent) throws IOException{
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20.0);
        root.setPadding(new Insets(10,10,10,10));
        Scene scene = new Scene(root,300,300);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Yeni Kategori Ekle..");
        stage.show();


        TextField categoryName = new TextField();
        categoryName.setPromptText("Kategori ismini giriniz..");
        categoryName.setFocusTraversable(false);

        Button addCategory = new Button("Onayla!");
        addCategory.setStyle("-fx-background-color: cornflowerblue; -fx-text-fill: white");

        root.getChildren().addAll(categoryName,addCategory);

        addCategory.setOnMouseClicked(event -> {
            try {
                String categoryNameText = URLEncoder.encode(categoryName.getText(),"utf8");
                response = new Common().doHttpPost(Common.baseURL+"addCategory.php?categoryName="+categoryNameText);
                if (Objects.equals(String.valueOf(response).trim(),"done")){
                    stage.close();
                    Common.createAlert(categoryName.getText()+" adlı kategori sisteme başarıyla eklendi!").showAndWait();
                    getAndFillCategory();
                    category_list.getSelectionModel().selectLast();

                    new Common().createLog(categoryName.getText()+" kategorisi sisteme eklendi");

                }else {
                    Common.createAlert("Err: "+response).showAndWait();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }


    private Desktop desktop = Desktop.getDesktop();
    public void fileChooser(MouseEvent mouseEvent) throws IOException {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files","*.png"));

        file = fileChooser.showOpenDialog(scene.getScene().getWindow());
        //desktop.open(file);
    }

    /* @TODO ***************   updateNewProduct.fxml   *************** */


    @FXML
    TableView<Product> table;
    @FXML
    TableColumn productIDCol,categoryIDCol,nameCol,costPriceCol,salePriceCol;
    @FXML
    VBox updateLayout;
    @FXML
    VBox updateNewProductContainer;



    Product alreadySelected = null;


    public void updateCategoryOfProduct(MouseEvent mouseEvent) throws IOException {
        final  int categoryID = category_list.getSelectionModel().getSelectedIndex()+1;
        response = new Common().
                doHttpPost(Common.baseURL+"updateCategory.php?categoryID="+categoryID+"&productID="+alreadySelected.getProductID());
        Common.createAlert(Objects.equals(response.toString().trim(), "done") ?"Kategori başarıyla güncellendi.":"Kategori güncellenemedi. Err:"+response).showAndWait();
        if(Objects.equals(response.toString().trim(),"done")){
            getAndFillTable();
            new Common().createLog(alreadySelected.getName()+" adlı ürünün kategorisi "+alreadySelected.getCategoryID()+" iken "+categoryID+" olarak değiştirildi");

        }
    }

    public void updateNameOfProduct(MouseEvent mouseEvent) throws IOException {
        response = new Common().doHttpPost(Common.baseURL+"updateProductName.php?productName="+ URLEncoder.encode(name.getText(),"utf-8")+"&productID="+alreadySelected.getProductID());
        Common.createAlert(Objects.equals(response.toString().trim(),"done")?"İsim başarıyla güncellendi":"İsim güncellenemedi. Err:"+response).showAndWait();
        if(Objects.equals(response.toString().trim(),"done")){
            getAndFillTable();
            new Common().createLog(alreadySelected.getCategoryID() +" IDli ürünün adı "+name.getText()+" olarak güncellendi");

        }
    }

    public void updateCostOfProduct(MouseEvent mouseEvent) throws IOException {
        response = new Common().doHttpPost(Common.baseURL+"updateCost.php?cost="+cost.getText()+"&productID="+alreadySelected.getProductID());
        Common.createAlert(Objects.equals(response.toString().trim(),"done")?"Maliyet başarıyla güncellendi":"Maliyet güncellenemedi. Err:"+response).showAndWait();
        if(Objects.equals(response.toString().trim(),"done")){
            getAndFillTable();
            new Common().createLog(alreadySelected.getName()+" adlı ürünün maliyeti "+alreadySelected.getCostPrice()+" iken "+cost.getText()+" olarak değiştirildi");

        }
    }

    public void updateSaleOfProduct(MouseEvent mouseEvent) throws IOException {
        response = new Common().doHttpPost(Common.baseURL+"updateSale.php?sale="+sale.getText()+"&productID="+alreadySelected.getProductID());
        Common.createAlert(Objects.equals(response.toString().trim(),"done")?"Satış fiyatı başarıyla güncellendi":"Maliyet güncellenemedi. Err:"+response).showAndWait();
        if(Objects.equals(response.toString().trim(),"done")){
            getAndFillTable();
            new Common().createLog(alreadySelected.getName()+" adlı ürünün satış fiyatı "+alreadySelected.getSalePrice()+" iken "+sale.getText()+" olarak güncellendi");

        }
    }

    public void updateImageOfProduct(MouseEvent mouseEvent) throws IOException {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files","*.png"));

        file = fileChooser.showOpenDialog(scene.getScene().getWindow());
        //desktop.open(file);
        updateImageOfProductCont();
    }

    public void updateImageOfProductCont() throws IOException {

        if(file == null){
            Common.createAlert("Resim seçmeden kayıt yapamazsın!").showAndWait();
            return;
        }

        String charset = "UTF-8";

        String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
        String CRLF = "\r\n"; // Line separator required by multipart/form-data.
        HttpURLConnection connection = (HttpURLConnection) (new URL(Common.baseURL+"updateImage.php")).openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        try (
                OutputStream output = connection.getOutputStream();
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
        ) {

            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"productID\"").append(CRLF);
            writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
            writer.append(CRLF).append(alreadySelected.getProductID()+"").append(CRLF).flush();



            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"image\"; filename=\"" + file.getName() + "\"").append(CRLF);
            writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(file.getName())).append(CRLF);
            writer.append("Content-Transfer-Encoding: binary").append(CRLF);
            writer.append(CRLF).flush();
            Files.copy(file.toPath(), output);
            output.flush();
            writer.append(CRLF).flush();


            writer.append("--" + boundary + "--").append(CRLF).flush();
            StringBuffer response = null;
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                String inputLine;
                response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();
            }
            System.out.println(response);

            new Common().createLog(alreadySelected.getName()+" adlı ürünün resmi güncellendi");

        }
    }

    public void seeImageOfProduct(MouseEvent mouseEvent) throws  IOException{
        Pane root = new Pane();
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webView.setPrefWidth(300);
        webView.setPrefHeight(300);
        root.getChildren().add(webView);
        String url = Common.baseURL+"images/"+alreadySelected.getProductID()+".png";
        System.out.println("You requested that link "+Common.baseURL+"images/"+alreadySelected.getProductID()+".png");

        webEngine.load(url);
        webEngine.reload();
        Scene scene = new Scene(root,300,300);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setMaxWidth(300);
        stage.setMaxHeight(300);
        stage.setMinWidth(300);
        stage.setMinHeight(300);
}
    public void deleteProduct(MouseEvent mouseEvent) throws IOException {
        StringBuffer response =
                new Common().doHttpPost(Common.baseURL+"deleteProduct.php?productID="+alreadySelected.getProductID());
        Common.createAlert(Objects.equals(response.toString().trim(),"done")?"Ürün başarıyla silindi":"Ürün silinemedi Err:"+response).showAndWait();
        if(Objects.equals(response.toString().trim(),"done")){
            getAndFillTable();
            new Common().createLog("{productID:"+alreadySelected.getProductID()+";categoryID:"+alreadySelected.getCategoryID()
                    +";productName:"+alreadySelected.getName()+";costPrice:"+alreadySelected.getCostPrice()+";salePrice:"+alreadySelected.getSalePrice()
                    +"} bilgilerine sahip ürün silindi ");

        }
    }

    /* @TODO ***************   addOrder.fxml   *************** */

    @FXML
    VBox tableContainer;
    @FXML
    VBox selectedTableBar;
    @FXML
    Text selectedTableBarText;
    @FXML
    VBox container;

    @FXML
    HBox orderContainer;

    @FXML
    VBox productContainer,complaintContainer;

    @FXML
    VBox soldProduct;

    @FXML
    HBox willBeSoldProduct,complaintButonContainer;

    @FXML
    Text soldProductTitle;

    @FXML
    VBox hr;

    @FXML
    HBox check;
    @FXML
    Text checkText;
    @FXML
    TextArea complaintText;
    @FXML
    javafx.scene.control.Button closeTableOrders,complaintButton,savecomplaint,cancelcomplaint;
    static int SELECTED_TABLE_NUMBER = 0;


    public void opencomplaint(MouseEvent mouseEvent) throws IOException  {

        complaintButonContainer.setVisible(false);
        complaintContainer.setVisible(true);
        tableContainer.setVisible(false);

    };

    public void savecomp(MouseEvent mouseEvent) throws IOException  {

        try {
            String complaint = URLEncoder.encode(complaintText.getText(),"utf8");

            response = new Common().doHttpPost(Common.baseURL+"addComplaint.php?complaintText=" + complaint + "&branchCode=" + 1 );
            if (Objects.equals(String.valueOf(response).trim(),"done")){
                Common.createAlert("Şikayet/İstek başarıyla sisteme eklendi!").showAndWait();
                complaintText.setText("");

            }else {
                Common.createAlert("Err:"+response).showAndWait();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    };

    public void cancelcomp(MouseEvent mouseEvent) throws IOException  {

        complaintButonContainer.setVisible(true);
        complaintContainer.setVisible(false);
        tableContainer.setVisible(true);

    };



    public void getAndFillAlreadyOrdered(){
        soldProduct.getChildren().removeAll(soldProduct.getChildren());
        soldProduct.getChildren().add(soldProductTitle);

        // Masanın siparişleri çekiliyor.
        try {
            response = new Common().doHttpPost(Common.baseURL+"getOrder.php?tableID="+Controller.SELECTED_TABLE_NUMBER);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        double check = 0.0; // Yeniden masa seçildiğinde önceki seçili masanın adisyonunu sıfırlamak için.

        // Çekilen siparişler pars ediliyor.
        JsonArray orderArr = new JsonParser().parse(String.valueOf(response)).getAsJsonArray();
        for (int f = 0; f < orderArr.size(); f++){
            JsonObject orderObj = orderArr.get(f).getAsJsonObject();
            Text line = new Text(); // siparişlerin herbir satırı.
            int orderNumber = f+1;
            final int productID =  orderObj.get("productID").getAsInt();
            final int orderID = orderObj.get("orderID").getAsInt();
            line.setText(orderNumber+"."+orderObj.get("name").getAsString()+"  =>"+orderObj.get("count").getAsString()+" adet x "+orderObj.get("sale").getAsString()+" TL");
            line.getStyleClass().add("urunlistele");
            soldProduct.getChildren().add(line);
            check += orderObj.get("sale").getAsDouble()*orderObj.get("count").getAsInt();


            line.setOnMouseClicked(clicked -> {
                try {
                    response = new Common().doHttpPost(Common.baseURL+"deleteOrder.php?orderID="+orderID);
                    if (Objects.equals(String.valueOf(response).trim(),"done")){
                        Common.createAlert(orderID+" sipariş numaralı "+orderObj.get("count").getAsInt()+
                                " adet "+orderObj.get("name").getAsString()+" siparişi başarıyla silindi.").showAndWait();
                        getAndFillAlreadyOrdered();
                    }else {
                        Common.createAlert(String.valueOf(response)).showAndWait();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        checkText.setText(String.valueOf(check));
    }


    public void showTableChoosingScreen(HBox tableRow){
        container.getChildren().add(0,tableContainer);

        tableRow.setPrefHeight(100); // Masa şemasını aynı boyutta göstermek için.

       container.getChildren().removeAll(orderContainer);
       selectedTableBar.setVisible(false);
    }

    /* @TODO ***************   soldProduct.fxml   *************** */

    @FXML
    TableView<SoldProduct> soldProductTable;
    @FXML
    TableColumn profitCol,countCol;

    @FXML
    VBox soldProductContainer;


   /* @TODO ***************   employee.fxml   *************** */

    @FXML
    TableView<Employee> employeeTable;
    @FXML
    TableColumn employeeNameCol,employeePhoneCol,employeeAddressCol,statuIDCol,employeeTcNoCol;
    @FXML
    VBox employeeContainer;
    @FXML
    ChoiceBox<String> empbrChoise;

    public void openAddNewEmployee(MouseEvent mouseEvent) throws IOException{
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20.0);
        root.setPadding(new Insets(10,10,10,10));
        Scene scene = new Scene(root,300,300);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Yeni Çalışan Ekle");
        stage.show();

        TextField employeeTcNo = new TextField();
        employeeTcNo.setPromptText("Tc no yazınız..");
        employeeTcNo.setFocusTraversable(false);


        TextField employeeName = new TextField();
        employeeName.setPromptText("Adını yazınız..");
        employeeName.setFocusTraversable(false);

        TextField employeePhone = new TextField();
        employeePhone.setPromptText("Telefon numarasını yazınız..");
        employeePhone.setFocusTraversable(false);

        TextField employeeAddress = new TextField();
        employeeAddress.setPromptText("Adresini giriniz..");
        employeeAddress.setFocusTraversable(false);

        ChoiceBox<String> statu_list = new ChoiceBox();
        response = new Common().doHttpPost(Common.baseURL+"getStatu.php");
        JsonArray statuArr = new JsonParser().parse(String.valueOf(response)).getAsJsonArray();
        for (int i = 0; i < statuArr.size(); i++){
            JsonObject statuObj = statuArr.get(i).getAsJsonObject();
            String statuName = statuObj.get("statuName").getAsString();
            statu_list.getItems().add(statuName);
        }
        statu_list.getSelectionModel().selectFirst();

        Button addEmployee = new Button("Ekle!");
        addEmployee.setStyle("-fx-background-color: cornflowerblue; -fx-text-fill: white");

        root.getChildren().addAll(employeeTcNo,employeeName,employeePhone,employeeAddress,statu_list,addEmployee);

        addEmployee.setOnMouseClicked(clicked -> {
            try {
                String employeeNameText = URLEncoder.encode(employeeName.getText(),"utf8");
                String employeePhoneText = URLEncoder.encode(employeePhone.getText(),"utf8");
                String employeeAddressText = URLEncoder.encode(employeeAddress.getText(),"utf8");
                int statuID = statu_list.getSelectionModel().getSelectedIndex()+1;
                response = new Common().doHttpPost(Common.baseURL+"addEmployee.php?employeeName="+employeeNameText+
                        "&employeePhone="+employeePhoneText+"&employeeAddress="+employeeAddressText+"&statuID="+statuID+"&employeeTcNo="+employeeTcNo.getText());
                if (Objects.equals(String.valueOf(response).trim(),"done")){
                    Common.createAlert(employeeName.getText()+" adlı çalışan başarıyla sisteme eklendi!").showAndWait();
                    getAndFillEmployee();
                    stage.close();
                    new Common().createLog(employeeNameText+" adlı çalışan sisteme eklendi");

                }else {
                    Common.createAlert("Err:"+response).showAndWait();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }


    public void openUpdateEmployee(MouseEvent mouseEvent) throws IOException{


        if (employeeTable.getSelectionModel().getSelectedItem() != null){

            Employee alreadySelected = employeeTable.getSelectionModel().getSelectedItem();

            VBox root = new VBox();
            root.setAlignment(Pos.CENTER);
            root.setSpacing(20.0);
            root.setPadding(new Insets(10,10,10,10));
            Scene scene = new Scene(root,300,300);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Çalışan Bilgileri Güncelle");
            stage.show();

            TextField employeeTcNo = new TextField();
            employeeTcNo.setText(alreadySelected.getEmployeeTcNo());
            employeeTcNo.setFocusTraversable(false);


            TextField employeeName = new TextField();
            employeeName.setText(alreadySelected.getEmployeeName());
            employeeName.setFocusTraversable(false);

            TextField employeePhone = new TextField();
            employeePhone.setText(alreadySelected.getEmployeePhone());
            employeePhone.setFocusTraversable(false);

            TextField employeeAddress = new TextField();
            employeeAddress.setText(alreadySelected.getEmployeeAddress());
            employeeAddress.setFocusTraversable(false);

            ChoiceBox<String> statu_list = new ChoiceBox();
            try {
                response = new Common().doHttpPost(Common.baseURL+"getStatu.php");
            } catch (IOException e) {
                e.printStackTrace();
            }
            JsonArray statuArr = new JsonParser().parse(String.valueOf(response)).getAsJsonArray();
            for (int i = 0; i < statuArr.size(); i++){
                JsonObject statuObj = statuArr.get(i).getAsJsonObject();
                String statuName = statuObj.get("statuName").getAsString();
                statu_list.getItems().add(statuName);
            }
        //    statu_list.getSelectionModel().select(alreadySelected.getStatuID()-1);

            Button addEmployee = new Button("güncelle!");
            addEmployee.setStyle("-fx-background-color: cornflowerblue; -fx-text-fill: white");

            root.getChildren().addAll(employeeTcNo,employeeName,employeePhone,employeeAddress,statu_list,addEmployee);

            addEmployee.setOnMouseClicked(clicked -> {
                try {
                    String employeeNameText = URLEncoder.encode(employeeName.getText(),"utf8");
                    String employeePhoneText = URLEncoder.encode(employeePhone.getText(),"utf8");
                    String employeeAddressText = URLEncoder.encode(employeeAddress.getText(),"utf8");
                    int statuID = statu_list.getSelectionModel().getSelectedIndex()+1;
                    response = new Common().doHttpPost(Common.baseURL+"updateEmployee.php?employeeName="+employeeNameText+
                            "&employeePhone="+employeePhoneText+"&employeeAddress="+employeeAddressText+"&statuID="+statuID+"&employeeID="+alreadySelected.getEmployeeID()+
                            "&employeeTcNo="+employeeTcNo.getText());
                    if (Objects.equals(String.valueOf(response).trim(),"done")){
                        Common.createAlert(employeeName.getText()+" adlı çalışan başarıyla güncellendi!").showAndWait();
                        stage.close();
                        getAndFillEmployee();
                        new Common().createLog(alreadySelected.getEmployeeID()+" ID'li çalışan bilgileri güncellendi.");

                    }else {
                        Common.createAlert("Err: "+response).showAndWait();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }else {
            Common.createAlert("Bilgilerini güncellemek üzere bir çalışan seçin!").showAndWait();
        }


    }

    public void fireEmployee(MouseEvent mouseEvent) throws  IOException{
       if (employeeTable.getSelectionModel().getSelectedItem() != null){
           response = new Common().doHttpPost(Common.baseURL+"fireEmployee.php?employeeID="+employeeTable.getSelectionModel().getSelectedItem().getEmployeeID());
           if (Objects.equals(String.valueOf(response).trim(),"done")){
               Common.createAlert(employeeTable.getSelectionModel().getSelectedItem().getEmployeeName()+" adlı çalışan işten çıkarıldı.").showAndWait();
               getAndFillEmployee();
               new Common().createLog(employeeTable.getSelectionModel().getSelectedItem().getEmployeeID()+" ID'li çalışan işten çıkarıldı");

           }else {
               Common.createAlert("Err: "+response).showAndWait();
           }
       }else {
           Common.createAlert("İşten çıkarılmak üzere bir çalışan seçin!").showAndWait();
       }
    }

    public void openAddUser(MouseEvent mouseEvent) throws IOException{

        if (employeeTable.getSelectionModel().getSelectedItem() != null){
            Employee alreadySelected = employeeTable.getSelectionModel().getSelectedItem();

            VBox root = new VBox();
            root.setAlignment(Pos.CENTER);
            root.setSpacing(20.0);
            root.setPadding(new Insets(10,10,10,10));
            Scene scene = new Scene(root,300,300);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Sisteme Giriş Yetkisi Ver");
            stage.show();

            PasswordField employeePasswd = new PasswordField();
            employeePasswd.setPromptText("Şifreyi giriniz");
            employeePasswd.setFocusTraversable(false);

            Button addUser = new Button("Onayla!");
            addUser.setStyle("-fx-background-color: cornflowerblue; -fx-text-fill: white");

            root.getChildren().addAll(employeePasswd,addUser);

            addUser.setOnMouseClicked(event -> {
                try {
                    String employeePasswdText = URLEncoder.encode(employeePasswd.getText(),"utf8");
                    response = new Common().doHttpPost(Common.baseURL+"addUser.php?employeeTcNo="+alreadySelected.getEmployeeTcNo()+"&employeePasswd="+employeePasswdText);
                    if (Objects.equals(String.valueOf(response).trim(),"done")){
                        stage.close();
                        Common.createAlert(alreadySelected.getEmployeeName()+" adlı çalışana sisteme giriş yetkisi verilmiştir!").showAndWait();
                        new Common().createLog(alreadySelected.getEmployeeID()+" ID'li çalışana sisteme giriş yetkisi verildi");

                    }else {
                        Common.createAlert("Err: "+response).showAndWait();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        }else {
            Common.createAlert("Giriş yetkisi vermek için bir çalışan seçin").showAndWait();
        }

    }

    /* @TODO ***************   soldProductt.fxml   *************** */
    @FXML
    TableView<SoldProduct> soldProducttTable;
    @FXML
    TableColumn categoryColumn,productColumn,countColumn,winColumn,profitColumn,branchColumn;
    @FXML
    DatePicker startDate,endDate;
    @FXML
    VBox soldProductContainerr;
    @FXML
    ChoiceBox<String> branchChoise = new ChoiceBox<>();
    @FXML
    Label wintext,profittext;

    public void showProductList(MouseEvent mouseEvent) throws IOException{

        categoryColumn.setCellValueFactory(new PropertyValueFactory<SoldProduct, String>("categoryName"));
        productColumn.setCellValueFactory(new PropertyValueFactory<SoldProduct,String>("productName"));
        countColumn.setCellValueFactory(new PropertyValueFactory<SoldProduct,Integer>("count"));
        winColumn.setCellValueFactory(new PropertyValueFactory<SoldProduct,Double>("win"));
        profitColumn.setCellValueFactory(new PropertyValueFactory<SoldProduct,Double>("profit"));
        branchColumn.setCellValueFactory(new PropertyValueFactory<SoldProduct,String>("branchName"));

        LocalDate start = startDate.getValue();
        LocalDate end = endDate.getValue();
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        String baslangic =  start.format(formatters);
        String bitis = end.format(formatters);
        double kar =0.0 ;
        double kazanc = 0.0;
        response = new Common().doHttpPost(Common.baseURL+"getSoldProduct.php?startDate=" + baslangic + "&endDate=" + bitis);

        JsonArray soldProductArr = new JsonParser().parse(String.valueOf(response)).getAsJsonArray();

        for (int i = 0; i < soldProductArr.size(); i++){
            JsonObject soldProductObj = soldProductArr.get(i).getAsJsonObject();
            if(Objects.equals(soldProductObj.get("BranchCode").getAsInt(),BranchCode)) {
                String categoryName = soldProductObj.get("categoryName").getAsString();
                String name = soldProductObj.get("productName").getAsString();
                double sale = soldProductObj.get("sale").getAsDouble();
                double cost = soldProductObj.get("cost").getAsDouble();
                int count = soldProductObj.get("count").getAsInt();
                double winnings = sale * count;
                System.out.println(winnings);
                double profit = (sale - cost) * count;
                soldProducttTable.getItems().add(new SoldProduct(categoryName, name,winnings, profit, count, branchName));
                kar = kar + profit;
                kazanc = kazanc + winnings;
            }

            wintext.setText(String.valueOf(kazanc) + " TL");
            profittext.setText(String.valueOf(kar) + " TL");
        }

    }

    /* @TODO ***************   statu.fxml   *************** */

    @FXML
    TableView<Statu> statuTable;
    @FXML
    TableColumn statuNameCol;
    @FXML
    VBox statuContainer;

    public void openAddNewStatu(MouseEvent mouseEvent) throws IOException{
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20.0);
        root.setPadding(new Insets(10,10,10,10));
        Scene scene = new Scene(root,300,300);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Yeni Statu Ekle");
        stage.show();

        TextField statuName = new TextField();
        statuName.setPromptText("Adını yazınız..");
        statuName.setFocusTraversable(false);

        Button addStatu = new Button("Ekle!");
        addStatu.setStyle("-fx-background-color: cornflowerblue; -fx-text-fill: white");

        root.getChildren().addAll(statuName,addStatu);

        addStatu.setOnMouseClicked(clicked -> {
            try {

                String statuNameText = URLEncoder.encode(statuName.getText(),"utf8");
                response = new Common().doHttpPost(Common.baseURL+"addStatu.php?statuName="+statuNameText);

                if (Objects.equals(String.valueOf(response).trim(),"done")){
                    Common.createAlert(statuName.getText()+" adlı statu başarıyla sisteme eklendi!").showAndWait();
                    stage.close();
                    getAndFillStatu();
                    new Common().createLog(statuNameText+" adlı statü sisteme eklendi");
                }else {
                    Common.createAlert("Err:"+response).showAndWait();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }


    public void openUpdateStatu(MouseEvent mouseEvent) throws IOException{


        if (statuTable.getSelectionModel().getSelectedItem() != null){

            Statu alreadySelected = statuTable.getSelectionModel().getSelectedItem();

            VBox root = new VBox();
            root.setAlignment(Pos.CENTER);
            root.setSpacing(20.0);
            root.setPadding(new Insets(10,10,10,10));
            Scene scene = new Scene(root,300,300);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Statu Bilgileri Güncelle");
            stage.show();

            TextField statuName = new TextField();
            statuName.setText(alreadySelected.getStatuName());
            statuName.setFocusTraversable(false);


            Button updateStatu = new Button("güncelle!");
            updateStatu.setStyle("-fx-background-color: cornflowerblue; -fx-text-fill: white");

            root.getChildren().addAll(statuName,updateStatu);

            updateStatu.setOnMouseClicked(clicked -> {
                try {
                    String statuNameText = URLEncoder.encode(statuName.getText(),"utf8");
                    response = new Common().doHttpPost(Common.baseURL+"updateStatu.php?statuName="+statuNameText+"&statuID="+alreadySelected.getStatuID());
                    if (Objects.equals(String.valueOf(response).trim(),"done")){
                        Common.createAlert(statuName.getText()+" adlı çalışan başarıyla güncellendi!").showAndWait();
                        stage.close();
                        getAndFillStatu();
                        new Common().createLog(alreadySelected.getStatuID()+" ID'li statünün adı güncellendi");

                    }else {
                        Common.createAlert("Err: "+response).showAndWait();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        }else {
            Common.createAlert("Bilgilerini güncellemek üzere bir çalışan seçin!").showAndWait();
        }

    }

    /* @TODO ***************   shift.fxml   *************** */

    @FXML
    VBox shiftContainer;

    @FXML
    HBox shiftProcessContainer;

    @FXML
    VBox shiftCont;




    public VBox createShiftGroup( int[] groupNoCounter, boolean alreadyDone) throws IOException {

        //final int NUMBER_OF_TABLE = 13;
        int NUMBER_OF_TABLE = 0;

        response = new Common().doHttpPost(Common.baseURL+"getTableCount.php");
        NUMBER_OF_TABLE = Integer.parseInt(String.valueOf(response));
        System.out.println("Sisteme kayıtlı masa sayısı: "+NUMBER_OF_TABLE);


        /*
        AlreadyDone :  Eğer bir grup, daha önceden oluşturulup veritabanına kaydedildiyse true döner ve fonksiyon sadece, grubu veritabanından çekip,
        fill fonksiyonu ile içini doldurur.
        Şayet bu grup ilk defa oluşturuluyorsa false döner,  CafeTable tablosuna bu gruba özel masalar eklenmesi gerekir. Bu durum if koşulunun içinde gerçekleşir.
         */
        if (!alreadyDone){ // Bu grup ilk defa yaratılıyor.
            // Her yeni group oluşturulduğunda veritabanaında CafeTable tablosunda o grup için masalar eklenir.
            // Grup silindiğinde bu satırlar da silinir.

            response = new Common().doHttpPost(Common.baseURL+"addTableOfShiftGroup.php?groupNo="+groupNoCounter[0]+"&tableCount="+NUMBER_OF_TABLE);

            if (Objects.equals(String.valueOf(response).trim(),"done")){
                Common.createAlert(groupNoCounter[0]+" numaralı grup için 13 tane masa sisteme eklendi!").showAndWait();
                new Common().createLog(groupNoCounter[0]+" numaralı grup masaları sisteme eklendi.");

            }
            if (Objects.equals(String.valueOf(response).trim(),"alreadyDone")){
                Common.createAlert(groupNoCounter[0]+" numaralı grup için 13 tane masa ZATEN eklenmiş!").showAndWait();
            }
            if (Objects.equals(String.valueOf(response).trim(),"Table count undefined!")){
                Common.createAlert("Masa sayısı bilinmediğinden ya da 0 olduğundan grup oluşturulamıyor!").showAndWait();
                throw new AssertionError("unreachable code reached");

            }

        }



        HBox group = new HBox();
        group.setPadding(new Insets(20,20,20,20));
        group.setAlignment(Pos.CENTER);

        Text groupNo = new Text();
        groupNo.setText("Group"+ groupNoCounter[0]);
        groupNo.setWrappingWidth((sceneContainer.getWidth()/2)-20);


        Text addEmployeeButton = new Text("[Add New Employee]");
        addEmployeeButton.setWrappingWidth((sceneContainer.getWidth()/2)-20);
        addEmployeeButton.setTextAlignment(TextAlignment.RIGHT);
        // Her grubun bir id'si var ve butonlara setId ile grup id'sini set ediyorum.
        // Gruba çalışan eklerken bu id yi get edeceğim.
        addEmployeeButton.setId(String.valueOf(groupNoCounter[0]));

        group.getChildren().addAll(groupNo,addEmployeeButton);
        shiftContainer.getChildren().add(group);
        // Gruba, çalışan ekleme barı eklendi.

        VBox employeeOfAboveGroup = new VBox();
        employeeOfAboveGroup.setStyle("-fx-background-color: darkkhaki");
        employeeOfAboveGroup.setPadding(new Insets(0,0,0,30));
        employeeOfAboveGroup.setAlignment(Pos.TOP_LEFT);
        shiftContainer.getChildren().add(employeeOfAboveGroup);
        // Gruptaki çalışanları gösteren bar eklendi.
        // addEmployeeButton tıklandığında çıkan ekranda çalışanlar seçilip onaylandıktan sonra, bu ara isimler eklenecek.


        addEmployeeButton.setOnMouseClicked((MouseEvent clicked2) -> { // Vardiyaya çalışan eklemek için yeni ekran açılıyor ve tabloya veri ekleniyor.

            VBox root = new VBox();
            root.setAlignment(Pos.CENTER);
            Scene scene = new Scene(root,300,300);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Vardiya Grubu İçin Çalışan Seç");
            stage.show();

            TableView<Employee> employeeTableView = new TableView<>();
            TableColumn employeeNameCol = new TableColumn("Çalışan İsmi");
            employeeNameCol.setPrefWidth(280);
            employeeTableView.getColumns().add(employeeNameCol);
            employeeNameCol.setText("Seçilen çalışan 0");
            TableColumn employeeIDCol = new TableColumn("ID");
            employeeIDCol.setPrefWidth(20);
            employeeTableView.getColumns().add(employeeIDCol);
            employeeIDCol.setText("ID");
            employeeNameCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("employeeName"));
            employeeIDCol.setCellValueFactory(new PropertyValueFactory<Employee,Integer>("employeeID"));
            employeeTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


            root.getChildren().add(employeeTableView);
            // çalışan tablosu eklendi.




            Button acceptButton = new Button();
            acceptButton.setStyle("-fx-background-color: cornflowerblue; -fx-text-fill: white");
            acceptButton.setText("Onayla!");

            root.getChildren().add(acceptButton);
            // Seçimi onaylama butonu eklendi.

            employeeTableView.getItems().removeAll(employeeTableView.getItems()); // tablo resetleniyor.

            try {
                response = new Common().doHttpPost(Common.baseURL+"getEmployeeForShift.php");
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Incoming message from getEmployeeForShift.php : "+response);
            JsonArray employeeArr = new JsonParser().parse(String.valueOf(response)).getAsJsonArray();
            for (int i = 0; i < employeeArr.size(); i++){
                JsonObject employeeObj = employeeArr.get(i).getAsJsonObject();

                String employeeName = employeeObj.get("employeeName").getAsString();
                int employeeID = employeeObj.get("employeeID").getAsInt();
                employeeTableView.getItems().add(new Employee(employeeName,employeeID));
            }

            employeeTableView.setOnMouseClicked(selection -> { // Gruba kaç tane eleman seçildiğini dinamik olarak gösteren fonksiyon.
                int effectedRows = employeeTableView.getSelectionModel().getSelectedItems().size();
                employeeNameCol.setText("Seçilen Çalışan "+effectedRows);
            });

            acceptButton.setOnMouseClicked(accepted -> { // Vardiya grubuna çalışan ekleyen fonksiyon.
                if (employeeTableView.getSelectionModel().getSelectedItems() != null){
                    ObservableList<Employee> alreadySelected = employeeTableView.getSelectionModel().getSelectedItems();
                    stage.close();
                    for (int i = 0; i < alreadySelected.size(); i++){

                        String groupNumber = addEmployeeButton.getId();
                        try {
                            response = new Common().doHttpPost(Common.baseURL+"addShiftGroup.php?employeeID="+alreadySelected.get(i).getEmployeeID()+
                                    "&groupNo="+groupNumber);
                            if (!Objects.equals(String.valueOf(response),"done")){
                                Common.createAlert(alreadySelected.get(i).getEmployeeName()+
                                        " ("+alreadySelected.get(i).getEmployeeID()+") isimli çalışanın vardiyası sisteme eklenemedi!" +
                                        "Err; "+response).showAndWait();
                                continue;
                            }
                            String employeeName = alreadySelected.get(i).getEmployeeName();
                            int employeeID = alreadySelected.get(i).getEmployeeID();
                            createEmployeeLineAndAddClickListener(employeeName,employeeID,employeeOfAboveGroup, Integer.parseInt(groupNumber));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }else {
                    Common.createAlert("En az bir çalışan seçilmeli!").showAndWait();
                }
            });

        });
        return employeeOfAboveGroup;
    }

    public void fillShiftGroup(JsonObject shiftGroupObj,VBox employeeOfAboveGroup, int groupNumber) throws IOException {



        int employeeID = 0;
        String employeeName = null;
        JsonArray employeeInfoArr = shiftGroupObj.get("employeeInfo").getAsJsonArray();
        for (int j = 0; j < employeeInfoArr.size(); j++){
            JsonObject employeeInfoObj = employeeInfoArr.get(j).getAsJsonObject();
            employeeID = employeeInfoObj.get("employeeID").getAsInt();
            employeeName = employeeInfoObj.get("employeeName").getAsString();

            createEmployeeLineAndAddClickListener(employeeName,employeeID,employeeOfAboveGroup,groupNumber);

        }

    }


    public void createEmployeeLineAndAddClickListener(String employeeName,int employeeID,VBox employeeOfAboveGroup,int groupNumber) throws IOException {

        // Çalışanın yetkili olduğu masalar veritabanından çekiliyor.
        response = new Common().doHttpPost(Common.baseURL+"getResponsibleFromTable.php?employeeID="+employeeID);
        JsonArray responsibeFromTableArr = new JsonParser().parse(String.valueOf(response)).getAsJsonArray();
        int numberOfTable = responsibeFromTableArr.size();

        System.out.println(employeeName+" is responsible from "+response);

        Text line = new Text(employeeName+" ("+employeeID+") - "+numberOfTable+" adet masa");
        employeeOfAboveGroup.getChildren().add(line);

        line.setOnMouseClicked(addResponsibleTable -> { // Gruba eklenen çalışana, sorumlu olacağı masalar veriliyor.
            HBox selectResponsibleTables = new HBox();
            Scene scene2 = new Scene(selectResponsibleTables,150,300);
            Stage stage2 = new Stage();
            stage2.setTitle("Select Table");
            stage2.setScene(scene2);
            stage2.show();

            TableView<CafeTable> tableTableView = new TableView<>();
                tableTableView.setMaxWidth(80);
                tableTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            TableColumn cafeTableIDCol = new TableColumn("tableID");
                cafeTableIDCol.setPrefWidth(80);
                cafeTableIDCol.setCellValueFactory(new PropertyValueFactory<CafeTable,Integer>("tableID"));
            tableTableView.getColumns().add(cafeTableIDCol);
            selectResponsibleTables.getChildren().add(tableTableView);


            Button acceptTables = new Button("Onayla!");
                acceptTables.setStyle("-fx-background-color: cornflowerblue; -fx-text-fill: white");
                acceptTables.setPrefWidth(70.0);
            selectResponsibleTables.getChildren().add(acceptTables);

            try {
                response = new Common().doHttpPost(Common.baseURL+"getTableForResponsibility.php?groupNo="+groupNumber);
                JsonArray tableArr = new JsonParser().parse(String.valueOf(response)).getAsJsonArray();
                for (int h = 0; h < tableArr.size(); h++){
                    JsonObject tableObj = tableArr.get(h).getAsJsonObject();
                    int tableID = tableObj.get("tableID").getAsInt();
                    tableTableView.getItems().add(new CafeTable(tableID));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            // Gruba eklenen çalışanın sorumlu olacağı masalar onaylandı.
            acceptTables.setOnMouseClicked(acceptTableRange -> {
                if (tableTableView.getSelectionModel().getSelectedItems() != null){
                    ObservableList<CafeTable> alreadySelected = tableTableView.getSelectionModel().getSelectedItems();
                    String selectedTableArr = "";
                    for (int z = 0; z < alreadySelected.size(); z++){
                        int tableID = alreadySelected.get(z).getTableID();

                        if (z+1 == alreadySelected.size())
                            selectedTableArr += tableID;
                        else
                            selectedTableArr += tableID+":";
                    }


                    try {
                        selectedTableArr = URLEncoder.encode(selectedTableArr,"utf8");
                        response = new Common().doHttpPost(Common.baseURL+"setResponsibleFromTable.php?employeeID="+employeeID+"&tables="+selectedTableArr+"&groupNo="+groupNumber);

                        if (Objects.equals(String.valueOf(response).trim(),"done")){
                            stage2.close();
                            Common.createAlert(alreadySelected.size()+" adet masa"+employeeName+" sorumluluğuna eklenmiştir.").showAndWait();
                            employeeOfAboveGroup.getChildren().remove(line);
                            createEmployeeLineAndAddClickListener(employeeName,employeeID,employeeOfAboveGroup,groupNumber);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }else {
                    Common.createAlert("En az bir masa seçmelisiniz!").showAndWait();
                }
            });

        });
    }

    /* @TODO ***************   prepareShift.fxml   *************** */

    @FXML
    VBox prepareShiftContainer;
    @FXML
    VBox prepareShiftCont;

    ArrayList<ShiftDay> shiftDayArrayList = new ArrayList<>();

    static int numberOfShiftDay = 0; // Vardiya listesinde kaç günün olduğunu tutuyor.

    static int numberOfAcceptedGroup = 0; // 1 günde kaç vardiyanın çalışacağını belirliyor.



    public void acceptShift(MouseEvent mouseEvent) throws IOException {

        System.out.println("numberOfSiftDay: "+numberOfShiftDay);
        System.out.println("numberOfAcceptedGroup: "+numberOfAcceptedGroup);
        System.out.println("shiftDayArrayList.size(): "+shiftDayArrayList.size());


        if (!shiftDayArrayList.isEmpty() && shiftDayArrayList.size() == numberOfShiftDay*numberOfAcceptedGroup){
            for (int i = 0; i < shiftDayArrayList.size(); i++){
                response = new Common().doHttpPost(Common.baseURL+"addShift.php?shiftDay="+URLEncoder.encode(shiftDayArrayList.get(i).getShiftDay(),"utf8")+
                        "&groupNo="+shiftDayArrayList.get(i).getGroupNo()+"&shiftRange="+URLEncoder.encode(shiftDayArrayList.get(i).getShiftHourRange(),"utf8")+
                        ((i==0)?"&first=OK":""));
                if (Objects.equals(String.valueOf(response),"done"))
                    continue;
                else {
                    Common.createAlert(shiftDayArrayList.get(i).getShiftDay()+" tarihli "+shiftDayArrayList.get(i).getGroupNo()+" numaralı grup vardiya" +
                            "sistemine eklenemedi : "+response);
                }
            }
        }else {
            Common.createAlert("Vardiyayı kaydetmek için tüm günleri ve grupları onaylamalısınız!").showAndWait();
        }

        new Common().createLog("Vardiya listesi güncellendi");

    }

    public void showShift(MouseEvent mouseEvent) throws IOException{
        VBox root = new VBox();
        Scene scene = new Scene(root,300,500);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        TableView<ShiftDay> shiftDayTableView = new TableView<>();
            shiftDayTableView.setPrefHeight(500.0);
        TableColumn shiftDayCol = new TableColumn("shiftDay");
        shiftDayTableView.getColumns().add(shiftDayCol);
        TableColumn groupNoCol = new TableColumn("groupNo");
        shiftDayTableView.getColumns().add(groupNoCol);
        TableColumn shiftRangeCol = new TableColumn("shiftRange");
        shiftDayTableView.getColumns().add(shiftRangeCol);

        shiftDayCol.setCellValueFactory(new PropertyValueFactory<ShiftDay,String>("shiftDay"));
        shiftDayCol.setPrefWidth(100.0);
        groupNoCol.setCellValueFactory(new PropertyValueFactory<ShiftDay,Integer>("groupNo"));
        groupNoCol.setPrefWidth(100.0);
        shiftRangeCol.setCellValueFactory(new PropertyValueFactory<ShiftDay,String>("shiftHourRange"));
        shiftRangeCol.setPrefWidth(100.0);

        root.getChildren().add(shiftDayTableView);


        // Vardiya listesi tabloya çekiliyor.
        response = new Common().doHttpPost(Common.baseURL+"getShift.php");
        JsonArray shiftArr = new JsonParser().parse(String.valueOf(response)).getAsJsonArray();
        for (int i = 0; i < shiftArr.size();i++){
            JsonObject shiftObj = shiftArr.get(i).getAsJsonObject();

            String shiftDay = shiftObj.get("shiftDay").getAsString();
            int groupNo = shiftObj.get("groupNo").getAsInt();
            String shiftRange = shiftObj.get("shiftRange").getAsString();

            shiftDayTableView.getItems().add(new ShiftDay(shiftDay,groupNo,shiftRange));
        }

        new Common().createLog("Vardiya listesi görüntülendi");

    }

    /* @TODO ***************   editCategory.fxml   *************** */

    @FXML
    TableView<EditCategory> editCategoryTableView;
    @FXML
    TableColumn  categoryNameCol;

    @FXML
    HBox categoryUpdateLayout;

    @FXML
    VBox updateTableCountContainer;
    @FXML
    VBox editCategoryContainer;

    @FXML
    TextField categoryUpdateText;

    EditCategory category;

    public void categoryAcceptClick() throws IOException{
        try {
            response = new Common().doHttpPost(Common.baseURL+"updateCategoryForEdit.php?categoryID="+category.getCategoryID()+"" +
                    "&categoryName="+ URLEncoder.encode(categoryUpdateText.getText(),"utf8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Objects.equals(String.valueOf(response),"done")){
          Alert alert = Common.createAlert(category.getCategoryName()+" kategorisi "+categoryUpdateText.getText()+" olarak değiştirildi!");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("style.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialog");

           alert.showAndWait();

            try {
                getAndFillCategoryForEdit();
                new Common().createLog(category.getCategoryID()+" ID'li kategori ismi güncellendi");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {
            Common.createAlert("Err; "+response).showAndWait();
        }

    }

    public void getAndFillCategoryForEdit() throws IOException {

        editCategoryTableView.getItems().removeAll(editCategoryTableView.getItems());

        response = new Common().doHttpPost(Common.baseURL+"getCategoryForEdit.php");
        System.out.println("Incoming message from getCategoryForEdit.php: "+response);

        JsonArray categoryArr = new JsonParser().parse(String.valueOf(response)).getAsJsonArray();
        for (int i = 0; i < categoryArr.size(); i++){
            JsonObject categoryObj = categoryArr.get(i).getAsJsonObject();
            int categoryID = categoryObj.get("categoryID").getAsInt();
            String categoryName = categoryObj.get("categoryName").getAsString();

            editCategoryTableView.getItems().add(new EditCategory(categoryID,categoryName));
        }
    }


   /* @TODO ***************   editBranch.fxml   *************** */

    @FXML
    Button updateBranch,addBranch,deleteBranch;
    @FXML
    TableView<Branch> branchTable;
    @FXML
   private TableColumn<Branch,Integer> CodeCol;
    @FXML
   private TableColumn<Branch,String> NameCol;
    @FXML
   private  TableColumn<Branch,String> AdressCol;
    @FXML
   private TableColumn<Branch,String> PhoneCol;
    @FXML
    TextField branchCode,branchName,branchAdress,branchPhone;

    public void updateBranch()throws IOException{

    }
    public void deleteBranch()throws IOException{

    }
    public void addBranch()throws IOException{

    }

    /* @TODO ***************   showComplaint.fxml   *************** */
    @FXML
    TableView<Complaint> complaintTable;
    @FXML
    TableColumn<Complaint,String> dateCol;
    @FXML
    TableColumn<Complaint,String> expCol;
    @FXML
    TableColumn<Complaint,String> brCol;
    @FXML
    ChoiceBox<String> brChoise;

     /* @TODO ***************   showLogRecords.fxml   *************** */

     @FXML
     TableView<LogRecords> LogTable;
     @FXML
     TableColumn<LogRecords,String> userTcNoCol;
     @FXML
    TableColumn<LogRecords,String> userNameCol,processCol,processTimeCol,userStatuCol;
     @FXML
     ChoiceBox<String> logbrChoise;

    /* @TODO ***************   ortak fonksiyonlar   *************** */

    @FXML
    VBox sceneContainer;


    public void changeLayout(@NamedArg("fxmlName") String fxmlName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("subUI/"+fxmlName + ".fxml"));
        fxmlLoader.setController(this);
        Pane newPane = fxmlLoader.load();
        sceneContainer.getChildren().setAll(newPane);
    }

    public void getAndFillCategory() throws IOException {

        StringBuffer responseCat = new Common().doHttpPost(Common.baseURL+"getCategory.php");
        JsonArray jsonArray = new JsonParser().parse(String.valueOf(responseCat)).getAsJsonArray();
        String[] category_list_array = new String[jsonArray.size()];

        for (int i = 0; i < jsonArray.size(); i++) {
            String jsonObject1 = jsonArray.get(i).getAsString();
            category_list_array[i] = jsonObject1;
        }

        if (category_list != null) {
            category_list.getItems().removeAll(category_list.getItems());
            category_list.getItems().addAll(category_list_array);

        }
       // ChoiceBox<Employee> deneme = new ChoiceBox<Employee>();
      //  List<Employee> calisanlar = new ArrayList<>() ;

        Employee calisan1 = new Employee("ahmet",1);
        Employee calisan2 = new Employee("ali",2);
        Employee calisan3 = new Employee("ayşe",3);
        calisanlar.add(calisan1);
        calisanlar.add(calisan2);
        calisanlar.add(calisan3);

    }

    public void getAndFillTable() throws  IOException{

        StringBuffer response = new Common().doHttpPost(Common.baseURL+"getProduct.php");

        JsonArray jsonArray = new JsonParser().parse(String.valueOf(response)).getAsJsonArray();
        table.getItems().removeAll(table.getItems());
        for (int i = 0; i < jsonArray.size(); i++){
            JsonObject obj = jsonArray.get(i).getAsJsonObject();
            String categoryName = obj.get("categoryName").getAsString();
            String name = obj.get("name").getAsString();
            String costPrice = obj.get("costPrice").getAsString();
            String salePrice = obj.get("salePrice").getAsString();
            int categotyID = obj.get("categoryID").getAsInt();
            table.getItems().add(new Product(categoryName,name,costPrice,salePrice,categotyID));
        }

    }

    public void getAndFillEmployee() throws  IOException{

        employeeTable.getItems().removeAll(employeeTable.getItems()); // tablo resetleniyor.

        response = new Common().doHttpPost(Common.baseURL+"getEmployee.php");

        JsonArray employeeArr = new JsonParser().parse(String.valueOf(response)).getAsJsonArray();
        for (int i = 0; i < employeeArr.size(); i++){
            JsonObject employeeObj = employeeArr.get(i).getAsJsonObject();
            int employeeID = employeeObj.get("employeeID").getAsInt();
            String employeeTcNo = employeeObj.get("employeeTcNo").getAsString();
            String employeeName = employeeObj.get("employeeName").getAsString();
            String employeePhone = employeeObj.get("employeePhone").getAsString();
            String employeeAddress  = employeeObj.get("employeeAddress").getAsString();
            String statuName = employeeObj.get("statuName").getAsString();
            employeeTable.getItems().add(new Employee(employeeID,employeeTcNo,employeeName,employeePhone,employeeAddress,statuName));
        }

    }

    public void getAndFillStatu() throws IOException{

        statuTable.getItems().removeAll(statuTable.getItems()); // tablo resetleniyor.

        response = new Common().doHttpPost(Common.baseURL+"getStatu.php");

        JsonArray statuArr = new JsonParser().parse(String.valueOf(response)).getAsJsonArray();
        for (int i = 0; i < statuArr.size(); i++){
            JsonObject statuObj = statuArr.get(i).getAsJsonObject();
            int statuID = statuObj.get("statuID").getAsInt();
            String statuName = statuObj.get("statuName").getAsString();

            statuTable.getItems().add(new Statu(statuID,statuName));
        }

    }



}

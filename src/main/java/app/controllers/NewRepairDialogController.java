package app.controllers;

import app.dao.DeviceDao;
import app.dao.OwnerDao;
import app.dao.handbooks.device.BrandDao;
import app.dao.handbooks.device.ModelDao;
import app.dao.handbooks.device.TypeDao;
import app.dao.handbooks.repair.RepairDao;
import app.dao.handbooks.repair.StatusDao;
import app.models.Device;
import app.models.LoggedInUser;
import app.models.Owner;
import app.models.Repair;
import app.utils.AutoSuggestTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class NewRepairDialogController {

    private static final Logger logger = LogManager.getLogger(NewRepairDialogController.class);

    @FXML private Label lDeviceID;

//    @FXML private Label lDeviType;
//    @FXML private Label lBrand;
//    @FXML private Label lModel;
//    @FXML private Label lSerialNumber;
//    @FXML private Label lCompleteness;
//    @FXML private Label lAppearance;
//    @FXML private Label lDefect;
//    @FXML private Label lNote;
//    @FXML private Label lSurname;
//    @FXML private Label lName;
//    @FXML private Label lPatronymic;
//    @FXML private Label lPhone;

    @FXML private AutoSuggestTextField tfDeviceType;
    @FXML private AutoSuggestTextField tfBrand;
    @FXML private AutoSuggestTextField tfModel;
    @FXML private AutoSuggestTextField tfSerialNumber;
    @FXML private AutoSuggestTextField tfCompleteness;
    @FXML private AutoSuggestTextField tfAppearance;
    @FXML private AutoSuggestTextField tfDefect;
    @FXML private AutoSuggestTextField tfNote;

    //todo add opportunity to select master

    @FXML private AutoSuggestTextField tfPhone;
    @FXML private AutoSuggestTextField tfSurname;
    @FXML private AutoSuggestTextField tfName;
    @FXML private AutoSuggestTextField tfPatronymic;


    /**
     * for store all linked values in one place
     */
//    private Hashtable<AutoSuggestTextField, HashtableValues> htFields = new Hashtable<>();

    @FXML
    private void initialize() {
        setTestData();
//
       setNewDeviceNumber();


//        fillHashTable();
        getSuggestions();
    }

    /**
     * gets suggestions for all text fields
     */
    private void getSuggestions() {
        //todo get suggestions from database for AutoSuggestTextField
        new TypeDao().getEntries(tfDeviceType);
        new BrandDao().getEntries(tfBrand);
        new ModelDao().getEntries(tfModel);
    }

    private void setTestData() {
        tfDeviceType.setText("Ноутбук");
        tfBrand.setText("Asus");
        tfModel.setText("X550");
        tfCompleteness.setText("Ноутбук, блок питания");
        tfAppearance.setText("Бывший в упротреблении");
        tfDefect.setText("Не включается");
        tfSerialNumber.setText("123abc#456");

        tfSurname.setText("Кобзарь");
        tfName.setText("Виталий");
        tfPatronymic.setText("Владимирович");
        tfPhone.setText("050-683-12-26");
    }

    private void setNewDeviceNumber() {
        lDeviceID.setText(String.valueOf(DeviceDao.getMaxId() + 1));
    }

    @FXML
    private void closeDlg(ActionEvent actionEvent){
        //получить источник события
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onBtnOk(ActionEvent actionEvent) {

        //если данные вводятся неправильно
//        if (!isEnteredCorrectly())
//            return;

        //get from dialog information about device owner
        Owner owner = createOwner();

        // check if owner exist
        OwnerDao ownerDao = new OwnerDao(owner);
        int ownerID = ownerDao.getId();

        if (ownerID == 0) {
            ownerID = ownerDao.save();
        }

        owner.setId(ownerID);

        Repair repair = createRepair();
        int repairID = new RepairDao(repair).save();

//        logger.debug("owner id: {}, repair id: {}", ownerID, repairID);

        Device device = createDevice(ownerID, repairID);
        new DeviceDao(device).save();

        closeDlg(actionEvent);
    }

    private Repair createRepair() {
        Repair repair = new Repair();
        int loggedInUserId = LoggedInUser.getLoggedInUser().getId();
        repair.setAcceptorId(loggedInUserId);
        //todo нужно гдето выбирать мастера для этого ремонта
        repair.setMasterId(loggedInUserId);
        repair.setStatusId(new StatusDao().getId("Оформлен"));
        repair.setDateOfAccept(LocalDateTime.now().toString());
        return repair;
    }

    private Device createDevice(int ownerId, int repairId) {
        //get from dialog fields information about device
        Device device = new Device();
        device.setType(tfDeviceType.getText());
        device.setBrand(tfBrand.getText());
        device.setModel(tfModel.getText());
        device.setSerialNumber(tfSerialNumber.getText());
        device.setOwnerId(ownerId);
        device.setDefect(tfDefect.getText());
        device.setRepairId(repairId);
        device.setCompleteness(tfCompleteness.getText());
        device.setAppearance(tfAppearance.getText());
        device.setNote(tfNote.getText());
        return device;
    }

    private Owner createOwner() {
        Owner owner = new Owner();
        owner.setSurname(tfSurname.getText());
        owner.setPatronymic(tfPatronymic.getText());
        owner.setName(tfName.getText());
        owner.setPhoneNumber(tfPhone.getText());
        return owner;
    }
}

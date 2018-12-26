package app.controllers;

import app.dao.DeviceDao;
import app.dao.OwnerDao;
import app.dao.handbooks.device.*;
import app.dao.handbooks.owner.NameDao;
import app.dao.handbooks.owner.PatronymicDao;
import app.dao.handbooks.owner.SurnameDao;
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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.LocalDateTime;


public class NewRepairDialogController {

    private static final Logger logger = LogManager.getLogger(NewRepairDialogController.class);

    @FXML private Label lDeviceID;
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

    @FXML
    private void initialize() {
        setTestData();
       setNewDeviceNumber();
        getSuggestions();
    }

    /**
     * gets suggestions for all text fields
     */
    private void getSuggestions() {
        new TypeDao().getEntries(tfDeviceType);
        new BrandDao().getEntries(tfBrand);
        new ModelDao().getEntries(tfModel);
        new CompletenessDao().getEntries(tfCompleteness);
        new AppearanceDao().getEntries(tfAppearance);
        new DefectDao().getEntries(tfDefect);

        new SurnameDao().getEntries(tfSurname);
        new NameDao().getEntries(tfName);
        new PatronymicDao().getEntries(tfPatronymic);
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

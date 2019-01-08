package app.controllers;

import app.dao.DeviceDao;
import app.dao.OwnerDao;
import app.dao.UserDao;
import app.dao.handbooks.device.*;
import app.dao.handbooks.owner.NameDao;
import app.dao.handbooks.owner.PatronymicDao;
import app.dao.handbooks.owner.SurnameDao;
import app.dao.handbooks.repair.RepairDao;
import app.dao.handbooks.repair.StatusDao;
import app.models.*;
import app.utils.AutoSuggestTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.LocalDateTime;
import java.util.List;


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

    @FXML private ComboBox cbMaster;

    @FXML private AutoSuggestTextField tfPhone;
    @FXML private AutoSuggestTextField tfSurname;
    @FXML private AutoSuggestTextField tfName;
    @FXML private AutoSuggestTextField tfPatronymic;

    private boolean okBtnPressed = false;

    @FXML
    private void initialize() {
        setTestData();
        setNewDeviceNumber();
        getSuggestions();
        fillMastersComboBox();
    }

    private void fillMastersComboBox() {
        logger.trace("");
        //разные мастера ремонтируют разные устройства
        //нужно знать тип устройства которое принимается
        //получить из базы всех мастеров который ремонтируют указанные типы устройств
        List<User> masters = UserDao.selectMasters();

        for (User u : masters) {
            String fio = String.format("%s %s %s", u.getSurname(), u.getName(), u.getPatronymic());
            cbMaster.getItems().add(fio);
        }
    }

    /**
     * gets suggestions for all text fields
     */
    private void getSuggestions() {
        new TypeDao().selectEntries(tfDeviceType);
        new BrandDao().selectEntries(tfBrand);
        new ModelDao().selectEntries(tfModel);
        new CompletenessDao().selectEntries(tfCompleteness);
        new AppearanceDao().selectEntries(tfAppearance);
        new DefectDao().selectEntries(tfDefect);

        new SurnameDao().selectEntries(tfSurname);
        new NameDao().selectEntries(tfName);
        new PatronymicDao().selectEntries(tfPatronymic);
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
        lDeviceID.setText(String.valueOf(DeviceDao.selectMaxId() + 1));
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
        int ownerID = ownerDao.selectId();

        if (ownerID == 0) {
            ownerID = ownerDao.insert();
        }

        owner.setId(ownerID);

        Repair repair = createRepair();
        int repairID = new RepairDao(repair).insert();

//        logger.debug("owner id: {}, repair id: {}", ownerID, repairID);

        Device device = createDevice(ownerID, repairID);
        new DeviceDao(device).insert();

        closeDlg(actionEvent);
        okBtnPressed = true;
    }

    private Repair createRepair() {
        Repair repair = new Repair();
        int loggedInUserId = LoggedInUser.getLoggedInUser().getId();
        repair.setAcceptorId(loggedInUserId);
        String fullName = cbMaster.getSelectionModel().getSelectedItem().toString();
        int masterId = UserDao.selectIdByFullName(fullName);
        repair.setMasterId(masterId);
        repair.setStatusId(new StatusDao().selectId(DeviceStatus.received));
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

    public boolean okBtnPressed() {
        return okBtnPressed;
    }
}

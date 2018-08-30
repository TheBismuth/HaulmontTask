package com.haulmont.UI;

import com.haulmont.UI.Validator.ValidatorNumber;
import com.haulmont.UI.Validator.ValidatorText;
import com.haulmont.dao.PatientDAO;
import com.haulmont.entity.Patient;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class PatientEditor extends FormLayout {

    private TextField firstName = new TextField("Имя");
    private TextField lastName = new TextField("Фамилия");
    private TextField middleName = new TextField("Отчество");
    private TextField cellPhone = new TextField("Телефон");

    private Button save = new Button("Сохранить");
    private Button cancel = new Button("Отменить");

    private PatientDAO service = new PatientDAO(Patient.class);
    private Patient patient;
    private MyUI myUI;
    private Binder<Patient> binder = new Binder<>(Patient.class);

    public PatientEditor(MyUI myUI) {
        this.myUI = myUI;

        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        addComponents(firstName, lastName, middleName, cellPhone, buttons);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        binder.forField(lastName).withValidator(new ValidatorText<>()).bind("lastName");
        binder.forField(firstName).withValidator(new ValidatorText<>()).bind("firstName");
        binder.forField(middleName).withValidator(new ValidatorText<>()).bind("middleName");
        binder.forField(cellPhone).withValidator(new ValidatorNumber<>()).bind("cellPhone");

        save.addClickListener(e -> this.save());
        cancel.addClickListener(e -> this.setVisible(false));
    }

    public void setCustomerSave(Patient patient) {
        this.patient = patient;
        binder.setBean(patient);
        setVisible(true);
        firstName.selectAll();
    }

    public void delete() {
        if (patient != null) {
            service.removeById(patient.getId());
            myUI.updateListPatient();
            setVisible(false);
        }
        if (patient == null) {
            Notification.show("Выберите пациента для удаления");
        }
    }

    private void save() {
        service.add(patient);
        myUI.updateListPatient();
        setVisible(false);
        firstName.selectAll();
    }

    public void update() {
        if (patient != null) {
            service.update(patient);
            myUI.updateListPatient();
            setVisible(false);
        } else {
            Notification.show("Выберите рецепт для удаления");
        }
    }

}





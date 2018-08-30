package com.haulmont.UI;

import com.haulmont.UI.Validator.ValidatorText;
import com.haulmont.dao.DoctorDAO;
import com.haulmont.entity.Doctor;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;


public class DoctorEditor extends FormLayout {

    private TextField firstName = new TextField("Имя");
    private TextField lastName = new TextField("Фамилия");
    private TextField middleName = new TextField("Отчество");
    private TextField specialization = new TextField("Специализация");

    private Button save = new Button("Сохранить");
    private Button cancel = new Button("Отменить");


    private DoctorDAO service = new DoctorDAO(Doctor.class);
    private Doctor doctor;
    private MyUI myUI;
    private Binder<Doctor> binder = new Binder<>(Doctor.class);

    public DoctorEditor(MyUI myUI) {
        this.myUI = myUI;

        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        addComponents(firstName, lastName, middleName, specialization, buttons);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        binder.forField(lastName).withValidator(new ValidatorText<>()).bind("lastName");
        binder.forField(firstName).withValidator(new ValidatorText<>()).bind("firstName");
        binder.forField(middleName).withValidator(new ValidatorText<>()).bind("middleName");
        binder.forField(specialization).withValidator(new ValidatorText<>()).bind("specialization");


        save.addClickListener(e -> this.save());
        cancel.addClickListener(e -> this.setVisible(false));

    }

    public void setDoctorSave(Doctor doctor) {
        this.doctor = doctor;
        binder.setBean(doctor);
        setVisible(true);
        firstName.selectAll();
    }

    public void delete() {
        if (doctor != null) {
            service.removeById(doctor.getId());
            myUI.updateListDoctor();
            setVisible(false);
        }
        if (doctor == null) {
            Notification.show("Выберите доктора для удаления");
        }
    }

    private void save() {
        service.add(doctor);
        myUI.updateListDoctor();
        setVisible(false);
        firstName.selectAll();
    }

    public void update() {
        if (doctor != null) {
            service.update(doctor);
            myUI.updateListDoctor();
            setVisible(false);
        } else {
            Notification.show("Выберите рецепт для удаления");
        }
    }
}



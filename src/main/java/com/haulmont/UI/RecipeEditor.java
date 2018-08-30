package com.haulmont.UI;

import com.haulmont.dao.DoctorDAO;
import com.haulmont.dao.RecipeDAO;
import com.haulmont.dao.PatientDAO;
import com.haulmont.entity.Doctor;
import com.haulmont.entity.Patient;
import com.haulmont.entity.Recipe;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class RecipeEditor extends FormLayout {

    private TextField description = new TextField("Описание");
    private ComboBox<Patient> patient = new ComboBox<>("Пациенты");
    private ComboBox<Doctor> doctor = new ComboBox<>("Доктора");
    private DateField createDate = new DateField("Дата создания");
    private DateField runOfValidity = new DateField("Дата окончания");
    private NativeSelect<Recipe.Priority> priority = new NativeSelect<>("Приоритет");

    Button save = new Button("Сохранить");
    private Button cancel = new Button("Отменить");

    private RecipeDAO recipeDAO = new RecipeDAO();
    private PatientDAO patientDAO = new PatientDAO(Patient.class);
    private DoctorDAO doctorDAO = new DoctorDAO(Doctor.class);
    private Recipe recipe = new Recipe();
    private MyUI myUI;
    private Binder<Recipe> binder = new Binder<>(Recipe.class);


    public RecipeEditor(MyUI myUI) {
        this.myUI = myUI;

        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        addComponents(description, patient, doctor, createDate, runOfValidity, priority, buttons);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        binder.bind(description, Recipe::getDescription, Recipe::setDescription);
        binder.bind(patient, Recipe::getPatient, Recipe::setPatient);
        binder.bind(doctor, Recipe::getDoctor, Recipe::setDoctor);

        binder.bind(createDate, Recipe::getCreationDate, Recipe::setCreationDate);
        createDate.setDateFormat("yyyy-MM-dd");
        createDate.setPlaceholder("yyyy-mm-dd");

        binder.bind(runOfValidity, Recipe::getRunOfValidity, Recipe::setRunOfValidity);
        runOfValidity.setDateFormat("yyyy-MM-dd");
        runOfValidity.setPlaceholder("yyyy-mm-dd");

        binder.bind(priority, Recipe::getPriority, Recipe::setPriority);

        patient.setItems(patientDAO.getAll());
        doctor.setItems(doctorDAO.getAll());
        priority.setItems(Recipe.Priority.values());

        save.addClickListener(e -> this.saveRecipe());
        cancel.addClickListener(e -> this.setVisible(false));
        setVisible(false);
    }


    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
        patient.setItems(patientDAO.getAll());
        doctor.setItems(doctorDAO.getAll());

        binder.setBean(recipe);

        setVisible(true);
        description.selectAll();
    }

    private void saveRecipe() {
        recipeDAO.add(recipe);
        myUI.updateListRecipe();
        setVisible(false);
        description.selectAll();
    }

    public void update() {
        if (recipe != null) {
            recipeDAO.update(recipe);
            myUI.updateListRecipe();
            setVisible(false);
        } else {
            Notification.show("Выберите рецепт для удаления");
        }
    }

    public void delete() {
        if (recipe != null) {
            recipeDAO.removeById(recipe.getId());
            myUI.updateListRecipe();
            setVisible(false);
        } else {
            Notification.show("Выберите рецепт для удаления");
        }
    }

}

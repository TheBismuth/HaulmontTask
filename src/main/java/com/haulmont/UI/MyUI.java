package com.haulmont.UI;

import javax.servlet.annotation.WebServlet;

import com.haulmont.dao.DoctorDAO;
import com.haulmont.dao.PatientDAO;
import com.haulmont.dao.RecipeDAO;
import com.haulmont.entity.Doctor;
import com.haulmont.entity.Patient;
import com.haulmont.entity.Recipe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.HasValue;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import java.util.List;


@Theme("mytheme")
public class MyUI extends UI {

    private PatientDAO servicePatient = new PatientDAO(Patient.class);
    private Grid<Patient> gridPatient = new Grid<>(Patient.class);
    private PatientEditor formPatient = new PatientEditor(this);


    private DoctorDAO serviceDoctor = new DoctorDAO(Doctor.class);
    private Grid<Doctor> gridDoctor = new Grid<>(Doctor.class);
    private DoctorEditor formDoctor = new DoctorEditor(this);


    private RecipeDAO serviceRecipe = new RecipeDAO();
    private Grid<Recipe> gridRecipe = new Grid<>(Recipe.class);
    private RecipeEditor formRecipe = new RecipeEditor(this);


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Label label = new Label("Больница");

        HorizontalLayout tablesLayout = new HorizontalLayout();

        VerticalLayout main = new VerticalLayout();

        TabSheet tabSheet = new TabSheet();
        tabSheet.addTab(patientsLayout()).setCaption("Пациенты");
        tabSheet.addTab(doctorsLayout()).setCaption("Доктора");
        tabSheet.addTab(recipeLayout()).setCaption("Рецепты");

        main.addComponents(tabSheet);

        setContent(main);
    }

    private VerticalLayout patientsLayout() {

        final VerticalLayout layout = new VerticalLayout();

        Label label1 = new Label("Пациенты");

        Button addPatient = new Button("Добавить пациента");
        addPatient.addClickListener(e -> {
            gridPatient.asSingleSelect().clear();
            formPatient.setCustomerSave(new Patient());
        });
        Button updatePatient = new Button("Редактировать пациента");
        updatePatient.addClickListener(e -> {
            gridPatient.asSingleSelect().clear();
            formPatient.update();
        });
        Button deletePatient = new Button("Удалить пациента");
        deletePatient.addClickListener(e -> {
            formPatient.delete();
        });

        HorizontalLayout toolbarPatient = new HorizontalLayout(addPatient, updatePatient, deletePatient);

        gridPatient.removeAllColumns();

        gridPatient.addColumn(Patient::getLastName).setCaption("Фамилия");
        gridPatient.addColumn(Patient::getFirstName).setCaption("Имя");
        gridPatient.addColumn(Patient::getMiddleName).setCaption("Отчество");
        gridPatient.addColumn(Patient::getCellPhone).setCaption("Телефон");

        HorizontalLayout mainPatient = new HorizontalLayout(gridPatient, formPatient);
        mainPatient.setSizeFull();
        gridPatient.setSizeFull();
        mainPatient.setExpandRatio(gridPatient, 1);

        layout.addComponents(mainPatient, toolbarPatient);

        updateListPatient();


        formPatient.setVisible(false);

        gridPatient.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
                formPatient.setVisible(false);
            } else {
                formPatient.setCustomerSave(event.getValue());
            }
        });

        return layout;
    }

    private VerticalLayout doctorsLayout() {

        final VerticalLayout doctorLayout = new VerticalLayout();

        Button addDoctor = new Button("Добавить доктора");
        addDoctor.addClickListener(e -> {
            gridDoctor.asSingleSelect().clear();
            formDoctor.setDoctorSave(new Doctor());
        });
        Button updateDoctor = new Button("Редактировать доктора");
        updateDoctor.addClickListener(e -> {
            gridDoctor.asSingleSelect().clear();
            formDoctor.update();
        });
        Button deleteDoctor = new Button("Удалить доктора");
        deleteDoctor.addClickListener(e -> {
            formDoctor.delete();
        });

        HorizontalLayout toolbarDoctor = new HorizontalLayout(addDoctor, updateDoctor, deleteDoctor);

        gridDoctor.removeAllColumns();

        gridDoctor.addColumn(Doctor::getLastName).setCaption("Имя");
        gridDoctor.addColumn(Doctor::getFirstName).setCaption("Фамилия");
        gridDoctor.addColumn(Doctor::getMiddleName).setCaption("Отчество");
        gridDoctor.addColumn(Doctor::getSpecialization).setCaption("Специализация");

        HorizontalLayout mainDoctor = new HorizontalLayout(gridDoctor, formDoctor);
        mainDoctor.setSizeFull();
        gridDoctor.setSizeFull();
        mainDoctor.setExpandRatio(gridDoctor, 1);


        doctorLayout.addComponents(mainDoctor, toolbarDoctor);

        updateListDoctor();

        formDoctor.setVisible(false);

        gridDoctor.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
                formDoctor.setVisible(false);
            } else {
                formDoctor.setDoctorSave(event.getValue());
            }
        });
        return doctorLayout;
    }

    private VerticalLayout recipeLayout() {
        final VerticalLayout recipe = new VerticalLayout();
        TextField filterText = new TextField();
        filterText.setPlaceholder("Фильтр по описанию");
        filterText.addValueChangeListener(this::onNameFilterTextChange);

        Button addRecipe = new Button("Добавить рецепт");
        addRecipe.addClickListener(e -> {

            gridRecipe.asSingleSelect().clear();
            formRecipe.setRecipe(new Recipe());
        });

        Button updateRecipe = new Button("Редактировать рецепт");
        updateRecipe.addClickListener(e -> {
            gridRecipe.asSingleSelect().clear();
            formRecipe.update();
        });
        Button deleteRecipe = new Button("Удалить рецепт");
        deleteRecipe.addClickListener(e -> {
            formRecipe.delete();
        });

        HorizontalLayout toolbar = new HorizontalLayout(addRecipe, updateRecipe, deleteRecipe, filterText);
        gridRecipe.removeAllColumns();

        gridRecipe.addColumn(Recipe::getDescription).setCaption("Описание");
        gridRecipe.addColumn(Recipe::getPatient).setCaption("Пациент");
        gridRecipe.addColumn(Recipe::getDoctor).setCaption("Доктор");
        gridRecipe.addColumn(Recipe::getCreationDate).setCaption("Дата создания");
        gridRecipe.addColumn(Recipe::getRunOfValidity).setCaption("Дата окончания");
        gridRecipe.addColumn(Recipe::getPriority).setCaption("Приоритет");

        HorizontalLayout mainRecipe = new HorizontalLayout(gridRecipe, formRecipe);
        mainRecipe.setSizeFull();
        gridRecipe.setSizeFull();
        mainRecipe.setExpandRatio(gridRecipe, 1);


        recipe.addComponents(mainRecipe, toolbar);

        updateListRecipe();

        formDoctor.setVisible(false);

        gridRecipe.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
                formRecipe.setVisible(false);
            } else {
                formRecipe.setRecipe(event.getValue());
            }
        });

        return recipe;
    }

    private void onNameFilterTextChange(HasValue.ValueChangeEvent<String> event) {
        ListDataProvider<Recipe> dataProvider = (ListDataProvider<Recipe>) gridRecipe.getDataProvider();
        dataProvider.setFilter(Recipe::getDescription, s -> caseInsensitiveContains(s, event.getValue()));
    }

    private Boolean caseInsensitiveContains(String where, String what) {
        return where.toLowerCase().contains(what.toLowerCase());
    }


    public void updateListPatient() {
        List<Patient> patientList = servicePatient.getAll();
        gridPatient.setItems(patientList);
    }

    public void updateListDoctor() {
        List<Doctor> doctorList = serviceDoctor.getAll();
        gridDoctor.setItems(doctorList);
    }

    public void updateListRecipe() {
        List<Recipe> recipeList = serviceRecipe.getAll();
        gridRecipe.setItems(recipeList);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}

package ru.anikin.rzd.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;
import org.springframework.beans.factory.annotation.Autowired;
import ru.anikin.rzd.models.Employee;
import ru.anikin.rzd.repo.EmployeeRepo;
import ru.anikin.rzd.views.components.EmployeeEditor;

@Route(value = "main")
//@Theme(value = Material.class)
public class MainView extends VerticalLayout {
    final private EmployeeRepo employeeRepo;
    private Grid<Employee> grid;

    private final TextField filter = new TextField("", "Type to filter");
    private final Button addNewButton = new Button("Add new");
    private final HorizontalLayout toolbar = new HorizontalLayout(filter, addNewButton);

    private final EmployeeEditor editor;

    @Autowired
    public MainView(EmployeeRepo employeeRepo, EmployeeEditor editor) {
        this.employeeRepo = employeeRepo;
        this.editor = editor;
        this.grid = new Grid<>(Employee.class);
        grid.setItems(employeeRepo.findAll());
        add(toolbar, grid, editor);

        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> showEmployee(e.getValue()));

        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editEmployee(e.getValue());
        });

        addNewButton.addClickListener(e -> editor.editEmployee(new Employee()));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            showEmployee(filter.getValue());
        });

        showEmployee("");
    }

    private void showEmployee(String name) {
        if (name.isEmpty()) {
            grid.setItems(employeeRepo.findAll());
        } else {
            grid.setItems(employeeRepo.findByName(name));
        }
    }
}

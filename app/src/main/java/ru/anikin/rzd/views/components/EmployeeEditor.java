package ru.anikin.rzd.views.components;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import ru.anikin.rzd.models.Employee;
import ru.anikin.rzd.repo.EmployeeRepo;

@SpringComponent
@UIScope
public class EmployeeEditor extends VerticalLayout implements KeyNotifier {
    private final EmployeeRepo employeeRepo;
    private Employee employee;

    private TextField name = new TextField("Name");
    private Button save = new Button("save", VaadinIcon.CHECK.create());
    private Button cancel = new Button("cancel");
    private Button delete = new Button("delete", VaadinIcon.TRASH.create());
    private HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    private Binder<Employee> binder = new Binder<>(Employee.class);
    @Setter
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public EmployeeEditor(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;

        add(name, actions);
        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editEmployee(employee));
        setVisible(false);
    }

    private void delete() {
        employeeRepo.delete(employee);
        changeHandler.onChange();
    }

    private void save() {
        employeeRepo.save(employee);
        changeHandler.onChange();
    }

    public void editEmployee(Employee newEmp) {
        if (newEmp == null) {
            setVisible(false);
            return;
        }

        if (newEmp.getId() != null) {
            employee = employeeRepo.findById(newEmp.getId()).orElse(newEmp);
        } else {
            employee = newEmp;
        }

        binder.setBean(employee);
        setVisible(true);
        name.focus();
    }
 }

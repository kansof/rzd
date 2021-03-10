package ru.anikin.rzd.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;

@Route(value = "")
//@Theme(value = Material.class)
public class testView extends AppLayout {
    public testView() {
        Image img = new Image("https://i.imgur.com/GPpnszs.png", "Vaadin Logo");
        Tabs tabs = new Tabs(new Tab("Home"), new Tab("About"));
        img.setHeight("44px");
        addToNavbar(img, tabs);
    }
}

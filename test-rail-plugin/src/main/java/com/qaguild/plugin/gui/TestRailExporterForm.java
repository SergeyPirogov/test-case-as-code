package com.qaguild.plugin.gui;

import com.intellij.openapi.project.Project;
import com.qaguild.plugin.Settings;

import javax.swing.*;

public class TestRailExporterForm {
    private JPanel rootPanel;
    private JTextField usernameTextField;
    private JTextField passwordPasswordField;
    private JTextField url;
    private JTextField projectIdTextFiled;
    private JTextField suiteIdTextField;
    private Settings settings;

    public TestRailExporterForm() {
    }

    public void createUI(Project project) {
        settings = Settings.getInstance(project);
        usernameTextField.setText(settings.getUserName());
        passwordPasswordField.setText(settings.getPassword());
        url.setText(settings.getApiUrl());
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public void apply() {
        settings.setUserName(usernameTextField.getText());
        settings.setPassword(passwordPasswordField.getText());
        settings.setApiUrl(url.getText());
        settings.setProjectId(Integer.valueOf(projectIdTextFiled.getText()));
        settings.setSuiteId(Integer.valueOf(suiteIdTextField.getText()));
    }

    public void reset() {
        usernameTextField.setText(settings.getUserName());
        passwordPasswordField.setText(settings.getPassword());
        url.setText(settings.getApiUrl());
        projectIdTextFiled.setText(String.valueOf(settings.getProjectId()));
        suiteIdTextField.setText(String.valueOf(settings.getSuiteId()));
    }

    public boolean isModified() {
        return !usernameTextField.getText().equals(settings.getUserName()) ||
                !passwordPasswordField.getText().equals(settings.getPassword()) ||
                !url.getText().equals(settings.getApiUrl());
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

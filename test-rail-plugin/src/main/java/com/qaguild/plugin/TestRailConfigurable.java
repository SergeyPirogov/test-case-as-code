package com.qaguild.plugin;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;
import com.qaguild.plugin.gui.TestRailExporterForm;

import javax.swing.*;

public class TestRailConfigurable implements Configurable {

    private TestRailExporterForm exporterForm;

    @SuppressWarnings("FieldCanBeLocal")
    private final Project mProject;

    public TestRailConfigurable(Project mProject) {
        this.mProject = mProject;
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "TestRail exporter plugin";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        exporterForm = new TestRailExporterForm();
        exporterForm.createUI(mProject);
        return exporterForm.getRootPanel();
    }

    @Override
    public boolean isModified() {
        return exporterForm.isModified();
    }

    @Override
    public void apply() throws ConfigurationException {
        exporterForm.apply();
    }

    @Override
    public void reset() {
        exporterForm.reset();
    }

    @Override
    public void disposeUIResources() {
        exporterForm = null;
    }
}

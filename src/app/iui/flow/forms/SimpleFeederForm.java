package app.iui.flow.forms;

import app.iui.StringResource;
import app.iui.flow.AbstractFormWithValidation;
import app.iui.flow.models.SimpleFeederModel;
import app.iui.validator.ValidationError;
import app.iui.validator.ValidatorFactory;
import app.iui.validator.Validators;
import constructor.objects.output.configuration.DocumentItemsSortMode;
import constructor.objects.simpler.configuration.SimplerConfiguration;
import util.Assert;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * @author Igor Usenko
 *         Date: 22.12.2010
 */
public class SimpleFeederForm extends AbstractFormWithValidation implements ActionListener {

    private static final String SPACE = " ";
    private static final int FIRST_COLUMN_WIDTH = 140;
    private static final int SECOND_COLUMN_WIDTH = 342;

    private static final String SIMPLER_EDITOR_CREATE_TITLE_KEY = "iui.simpler.feeder.form.create.feeder.title.key";
    private static final String SIMPLER_EDITOR_EDIT_TITLE_KEY = "iui.simpler.feeder.form.edit.feeder.title.key";
    private static final String SIMPLER_EDITOR_IDENTIFIER_LABEL_KEY = "iui.simpler.feeder.form.identifier.label.key";
    private static final String SIMPLER_EDITOR_FEED_URL_LABEL_KEY = "iui.simpler.feeder.form.feed.url.label.key";
    private static final String SIMPLER_EDITOR_STORE_DAYS_LABEL_KEY = "iui.simpler.feeder.form.store.days.label.key";
    private static final String SIMPLER_EDITOR_BRANCH_LABEL_KEY = "iui.simpler.feeder.form.branch.label.key";
    private static final String SIMPLER_EDITOR_OUTPUT_FILE_LABEL_KEY = "iui.simpler.feeder.form.output.file.label.key";
    private static final String SIMPLER_EDITOR_SORT_LABEL_KEY = "iui.simpler.feeder.form.sort.label.key";
    private static final String SIMPLER_EDITOR_CRITERION_LABEL_KEY = "iui.simpler.feeder.form.criterion.label.key";
    private static final String SIMPLER_EDITOR_COVER_URL_LABEL_KEY = "iui.simpler.feeder.form.cover.url.label.key";
    private static final String ONLY_FOR_FRIEF_RSS = "iui.simpler.feeder.form.only.for.brief.rss.label.key";
    private static final String COMMON_PARMS = "iui.simpler.feeder.form.common.parameters.label.key";

    private static final String NEXT = "iui.simpler.feeder.form.next.button.label";
    private static final String BACK = "iui.simpler.feeder.form.back.button.label";

    private final SimpleFeederModel model;
    private final Listener listener;

    private JTextField idField;
    private JTextField feedUrlField;
    private JTextField storeDaysField;
    private JTextField criterionField;
    private JTextField branchField;
    private JCheckBox sortCheck;
    private JTextField outputField;
    private JTextField coverField;

    private JButton next;
    private JButton back;

    public SimpleFeederForm(final JFrame _owner, final ValidatorFactory _validatorFactory, final int _width, final int _height, final StringResource _stringResource, final SimpleFeederModel _model, final Listener _listener) {
        super(_owner, _validatorFactory, _width, _height, _stringResource);

        Assert.notNull(_model, "Model is null");
        this.model = _model;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        setCaption(getString(this.model.isBlank() ? SIMPLER_EDITOR_CREATE_TITLE_KEY : SIMPLER_EDITOR_EDIT_TITLE_KEY));
        setData();
    }

    public void actionPerformed(final ActionEvent _event) {
        Assert.notNull(_event, "Event is null");

        if (_event.getSource() == this.next) {
            onApprove();
        }

        if (_event.getSource() == this.back) {
            onDiscard();
        }
    }

    protected void onApprove() {
        ValidationError validationError = validation();

        if (validationError != null) {
            showValidationError(validationError);
        } else {
            hideValidationError();

            this.model.approve();
            this.model.setConfiguration(getConfiguration());
            this.listener.submit(this.model);
        }
    }

    protected void onDiscard() {
        hideValidationError();

        this.model.reject();
        this.listener.submit(this.model);
    }

    protected void addValidators(final Map<JTextField, Validators> _validators) {
        Assert.notNull(_validators, "Validators is null");

        Validators idValidators = new Validators();
        idValidators.addValidator(this.validatorFactory.notEmpty());
        idValidators.addValidator(this.validatorFactory.latinSymbolsAndDigits());
        _validators.put(this.idField, idValidators);

        Validators feedUrlValidators = new Validators();
        feedUrlValidators.addValidator(this.validatorFactory.notEmpty());
        feedUrlValidators.addValidator(this.validatorFactory.urlValid());
        _validators.put(this.feedUrlField, feedUrlValidators);

        Validators storeDaysValidators = new Validators();
        storeDaysValidators.addValidator(this.validatorFactory.notEmpty());
        storeDaysValidators.addValidator(this.validatorFactory.integerInRange(1, 365));
        _validators.put(this.storeDaysField, storeDaysValidators);

        Validators branchValidators = new Validators();
        branchValidators.addValidator(this.validatorFactory.validFileName());
        _validators.put(this.branchField, branchValidators);

        Validators outputFileNameValidators = new Validators();
        outputFileNameValidators.addValidator(this.validatorFactory.notEmpty());
        outputFileNameValidators.addValidator(this.validatorFactory.validFileName());
        _validators.put(this.outputField, outputFileNameValidators);

        Validators coverUrlValidators = new Validators();
        coverUrlValidators.addValidator(this.validatorFactory.urlValid());
        _validators.put(this.coverField, coverUrlValidators);
    }

    protected void setupControls() {
        getContentPanel().add(new JLabel(getString(COMMON_PARMS)), createConstraint().maximumWidth().wrap().toString());

        getContentPanel().add(new JLabel(getString(SIMPLER_EDITOR_IDENTIFIER_LABEL_KEY)), createConstraint().width(FIRST_COLUMN_WIDTH).split(2).toString());
        getContentPanel().add(this.idField = new JTextField(SPACE), createConstraint().width(SECOND_COLUMN_WIDTH).wrap().toString());
        getContentPanel().add(new JLabel(getString(SIMPLER_EDITOR_FEED_URL_LABEL_KEY)), createConstraint().width(FIRST_COLUMN_WIDTH).split(2).toString());
        getContentPanel().add(this.feedUrlField = new JTextField(SPACE), createConstraint().width(SECOND_COLUMN_WIDTH).wrap().toString());
        getContentPanel().add(new JLabel(getString(SIMPLER_EDITOR_STORE_DAYS_LABEL_KEY)), createConstraint().width(FIRST_COLUMN_WIDTH).split(2).toString());
        getContentPanel().add(this.storeDaysField = new JTextField(SPACE), "w 40!, wrap");
        getContentPanel().add(new JLabel(getString(SIMPLER_EDITOR_BRANCH_LABEL_KEY)), createConstraint().width(FIRST_COLUMN_WIDTH).split(2).toString());
        getContentPanel().add(this.branchField = new JTextField(SPACE), createConstraint().width(SECOND_COLUMN_WIDTH).wrap().toString());
        getContentPanel().add(new JLabel(getString(SIMPLER_EDITOR_OUTPUT_FILE_LABEL_KEY)), createConstraint().width(FIRST_COLUMN_WIDTH).split(2).toString());
        getContentPanel().add(this.outputField = new JTextField(SPACE), createConstraint().width(SECOND_COLUMN_WIDTH).wrap().toString());
        getContentPanel().add(new JLabel(getString(SIMPLER_EDITOR_COVER_URL_LABEL_KEY)), createConstraint().width(FIRST_COLUMN_WIDTH).split(2).toString());
        getContentPanel().add(this.coverField = new JTextField(SPACE), createConstraint().width(SECOND_COLUMN_WIDTH).wrap().toString());
        getContentPanel().add(this.sortCheck = new JCheckBox(getString(SIMPLER_EDITOR_SORT_LABEL_KEY)), createConstraint().width(FIRST_COLUMN_WIDTH).wrap().toString());

        getContentPanel().add(new JLabel(getString(ONLY_FOR_FRIEF_RSS)), createConstraint().maximumWidth().wrap().toString());

        getContentPanel().add(new JLabel(getString(SIMPLER_EDITOR_CRITERION_LABEL_KEY)), createConstraint().width(FIRST_COLUMN_WIDTH).split(2).toString());
        getContentPanel().add(this.criterionField = new JTextField(SPACE), createConstraint().width(SECOND_COLUMN_WIDTH).wrap().toString());

        this.next = new JButton(getString(NEXT));
        getContentPanel().add(this.next, createConstraint().largeButton().verticalGap().alignRight().wrap().toString());
        setDefaultButton(this.next);
        this.next.addActionListener(this);

        this.back = new JButton(getString(BACK));
        addFooterButton(this.back, createConstraint().mediumButton().toString());
        this.back.addActionListener(this);
    }

    private void setData() {
        this.idField.setText(this.model.getConfiguration().getId());
        this.feedUrlField.setText(this.model.getConfiguration().getFeedUrl());
        this.storeDaysField.setText(this.model.getConfiguration().getStoreDays());
        this.branchField.setText(this.model.getConfiguration().getBranch());
        this.outputField.setText(this.model.getConfiguration().getOutName());
        this.criterionField.setText(getXPathCriterion());
        this.coverField.setText(this.model.getConfiguration().getCoverUrl());
        this.sortCheck.setSelected(this.model.getConfiguration().getFromNewToOld() == DocumentItemsSortMode.FROM_NEW_TO_OLD);
    }

    private String getXPathCriterion() {
        return this.model.getConfiguration().getCriterions().isEmpty() ? "" : this.model.getConfiguration().getCriterions();
    }

    public SimplerConfiguration getConfiguration() {
        SimplerConfiguration result = new SimplerConfiguration();

        result.setId(this.idField.getText());
        result.setAttributeFeedUrl(this.feedUrlField.getText());
        result.setAttributeStoreDays(this.storeDaysField.getText());

        if (!this.criterionField.getText().isEmpty()) {
            result.setElementXPath(this.criterionField.getText());
        }

        if (!this.branchField.getText().isEmpty()) {
            result.setAttributeBranch(this.branchField.getText());
        }

        result.setAttributeOutName(this.outputField.getText());

        result.setAttributeFromNewToOld(this.sortCheck.isSelected() ? SimplerConfiguration.YES_TOKEN : SimplerConfiguration.NO_TOKEN);

        if (!this.coverField.getText().isEmpty()) {
            result.setAttributeCoverUrl(this.coverField.getText());
        }

        return result;
    }

    public interface Listener {
        void submit(SimpleFeederModel _model);
    }
}

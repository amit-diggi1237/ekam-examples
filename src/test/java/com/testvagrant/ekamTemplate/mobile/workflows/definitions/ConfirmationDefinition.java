package com.testvagrant.ekamTemplate.mobile.workflows.definitions;

import com.testvagrant.ekam.commons.LayoutInitiator;
import com.testvagrant.ekamTemplate.data.models.UseCase;
import com.testvagrant.ekamTemplate.mobile.screens.android.ConfirmationScreen;
import com.testvagrant.ekamTemplate.mobile.workflows.WorkflowDefinition;

public class ConfirmationDefinition extends WorkflowDefinition {

    public ConfirmationDefinition(UseCase useCase) {
        super(useCase);
    }

    @Override
    public ConfirmationDefinition next() {
        return this;
    }

    @Override
    public ConfirmationScreen create() {
        return LayoutInitiator.Screen(ConfirmationScreen.class);
    }
}

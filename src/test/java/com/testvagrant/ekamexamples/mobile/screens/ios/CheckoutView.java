package com.testvagrant.ekamexamples.mobile.screens.ios;

import com.testvagrant.ekam.atoms.mobile.MobileScreen;
import com.testvagrant.ekam.commons.LayoutInitiator;
import com.testvagrant.ekam.reports.annotations.MobileStep;
import com.testvagrant.ekamexamples.data.models.Address;
import com.testvagrant.ekamexamples.mobile.screens.android.ConfirmationScreen;
import org.openqa.selenium.By;

public class CheckoutView extends MobileScreen {

  By continueCheckout = queryByName("test-CONTINUE");
  By cancel = queryByName("test-CANCEL");
  By finish = queryByName("test-FINISH");

  @MobileStep(description = "Checkout")
  public CheckoutView checkout(Address address) {
    enterAddress(address).continueCheckout().finish();
    return this;
  }

  @MobileStep(description = "Enter Address")
  public CheckoutView enterAddress(Address address) {
    Information information = new Information();
    textbox(information.firstName).setText(address.getFirstName());
    textbox(information.lastName).setText(address.getLastName());
    textbox(information.zipcode).setText(address.getZipCode());
    return this;
  }

  @MobileStep(description = "Continue Checkout")
  public CheckoutView continueCheckout() {
    iosDeviceDriver.hideKeyBoard();
    element(continueCheckout).click();
    return this;
  }

  @MobileStep(description = "clikc cancel")
  public ConfirmationScreen cancel() {
    element(cancel).click();
    return LayoutInitiator.Screen(ConfirmationScreen.class);
  }

  @MobileStep(description = "Click finish")
  public CheckoutView finish() {
    element(finish).click();
    return LayoutInitiator.Screen(CheckoutView.class);
  }

  private class Information {
    By firstName = queryByName("test-First Name");
    By lastName = queryByName("test-Last Name");
    By zipcode = queryByName("test-Zip/Postal Code");
  }
}

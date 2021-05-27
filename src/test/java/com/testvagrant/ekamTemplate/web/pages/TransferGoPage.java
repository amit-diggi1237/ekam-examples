package com.testvagrant.ekamTemplate.web.pages;

import com.google.common.base.CharMatcher;
import com.testvagrant.ekam.atoms.web.WebPage;
import com.testvagrant.ekam.commons.annotations.SwitchView;
import com.testvagrant.ekam.commons.annotations.WebStep;
import com.testvagrant.ekam.commons.injectors.Injectors;
import org.awaitility.Awaitility;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.util.regex.Pattern;

public class TransferGoPage extends WebPage {

  private final By sendingFromCountrySelector = query("(//*[contains(@class, 'country-input')])[1]");
  private final By receivingInCountrySelector = query("(//*[contains(@class, 'country-input')])[2]");
  private final By sendingFromTextboxSelector = query("(//input[@autocomplete='amount-input'])[1]");
  private final By receivingInTextboxSelector = query("(//input[@autocomplete='amount-input'])[2]");
  private final By acceptCookiesSelector = query("//a[contains(text(), 'Accept')]");

  @WebStep(description = "Accept Cookies")
  public TransferGoPage acceptCookies() {
    element(acceptCookiesSelector).waitUntilDisplayed();
    element(acceptCookiesSelector).click();
    return this;
  }

  @SwitchView(view = TransferGoMobileView.class, useInjector = Injectors.WEB_PAGE_INJECTOR)
  @WebStep(keyword = "And", description = "selects sending from country")
  public TransferGoPage selectSendingFromCountry(String country) {
    String initialSendingFromValue = getSendingFromValue();
    textbox(sendingFromCountrySelector).setText(country);
    selectCountry(country);
    textbox(sendingFromTextboxSelector)
        .waitUntilAttributeNotToBe("value", getAmount(initialSendingFromValue));
    return this;
  }

  @SwitchView(view = TransferGoMobileView.class, useInjector = Injectors.WEB_PAGE_INJECTOR)
  @WebStep(keyword = "And", description = "select sending from amount")
  public TransferGoPage setSendingFromAmount(String amount) {
    textbox(sendingFromTextboxSelector).setText(Keys.COMMAND + "A");
    textbox(sendingFromTextboxSelector).setText(amount);
    return this;
  }

  @SwitchView(view = TransferGoMobileView.class, useInjector = Injectors.WEB_PAGE_INJECTOR)
  @WebStep(keyword = "And", description = "enters receiving amount")
  public Double getReceivingInAmount() {
    String rate = textbox(receivingInTextboxSelector).getAttributeValue("value");
    if (!rate.isEmpty()) {
      String formattedAmount = rate.substring(1).replaceAll(" ", "").trim();
      return Double.parseDouble(formattedAmount);
    }

    return 0.00;
  }

  @SwitchView(view = TransferGoMobileView.class, useInjector = Injectors.WEB_PAGE_INJECTOR)
  @WebStep(keyword = "When", description = "selects receiving country")
  public TransferGoPage selectReceivingInCountry(String country) {
    String initialReceivingInValue = getReceivingInValue();
    textbox(receivingInCountrySelector).setText(country);
    selectCountry(country);

    Awaitility.waitAtMost(Duration.ofSeconds(10))
        .until(() -> !getReceivingInValue().contains(initialReceivingInValue));

    return this;
  }

  private void selectCountry(String country) {
    String countrySelector = "//span[@class='country-name' and text() = 'PLACEHOLDER']";
    element(query(countrySelector.replace("PLACEHOLDER", country))).click();
  }

  private String getReceivingInValue() {
    String value = textbox(receivingInTextboxSelector).getAttributeValue("value");
    CharMatcher matcher = CharMatcher.inRange('0', '9').or(CharMatcher.is('.')).precomputed();
    return matcher.retainFrom(value).trim();
  }

  private String getSendingFromValue() {
    return textbox(sendingFromTextboxSelector).getAttributeValue("value");
  }

  private String getAmount(String value) {
    Pattern pattern = Pattern.compile("\\d+(\\.\\d{2})?|\\.\\d{2}");
    String currency = pattern.matcher(value).replaceAll("");
    return value.replaceAll(currency, "").trim();
  }
}

import constants.Strings;
import dbfactory.ChannelScripts;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.common.CommonFun;
import utils.extentreports.ExtentTestManager;

import java.sql.SQLException;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class UpdateChannelTest extends BaseClass {

    String CheckerID;
    String channelName;
    String channelDescription;
    String updatedChannelDescription;
    ChannelCheckerTest checkerTest = new ChannelCheckerTest();


    @Test(priority = 1, groups = "positive")
    public void testLoginPositive() {
        ExtentTestManager.startTest("testLoginPositive",
                "Verify user should be able to login using valid credentials");
        loginPage.login(userName, userPass);
        loginPage.enterOtp(staticOtp);
        navigation.navigateToChannel();
        //Assert.assertNotEquals(addChannelPage.getAlertMessage(),Strings.somethingHasWentWrongText);
    }

    @Test(priority = 2, groups = "positive")
    public void testChannelCreationToUpdate() throws SQLException {
        ExtentTestManager.startTest("testFieldValidationWithOnlyChannelName",
                "Verify that user should be able to create channel");
        addChannelPage.clickAddButton();
        addChannelPage.emptyAllInputFields();
        channelName = CommonFun.getRandomName("AutomatedChannel");
        channelDescription = CommonFun.getRandomName("AutomatedDescription");
        addChannelPage.enterChannelName(channelName);
        addChannelPage.enterChannelDesc(channelDescription);
        addChannelPage.clickSubmitButton();
        addChannelPage.verifyDialogBoxMessage();
        if (!addChannelPage.verifyDialogBoxMessage().toLowerCase().contains("success")) {
            addChannelPage.clickDialogBoxOkButton();
            addChannelPage.clickSubmitButton();
            addChannelPage.verifyDialogBoxMessage();
            Assert.assertTrue(addChannelPage.verifyDialogBoxMessage().toLowerCase().contains("success"),"Channel was not created due to"+addChannelPage.getCheckerID());
        }
        CheckerID = addChannelPage.getCheckerID();
        Assert.assertEquals(ChannelScripts.verifyNewlyCreatedChannelInDB(channelName),1,"No Channel Record is found in Database against Name"+channelName);
        addChannelPage.clickDialogBoxOkButton();
        assertThat(page.getByLabel("Breadcrumb")).containsText("Channel");
    }

    @Test(priority = 3, groups = "positive",dependsOnMethods = "testChannelCreationToUpdate")
    public void checkerApproval() throws SQLException {
        ExtentTestManager.startTest("testFieldValidationWithOnlyChannelName",
                "Verify that checker user can approve new created channel");
        checkerTest.splitCheckIdAndName(CheckerID);
        checkerTest.testLoginPositive();
        checkerTest.approveRecordFromChecker();
    }

    @Test(priority = 4, groups = "positive")
    public void updateNewlyCreatedChannelAndApproveItFromChecker() {
        addChannelPage.enterChannelName(channelName);
        addChannelPage.clickOnSearchButton();
        addChannelPage.clickOnEditButton();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(addChannelPage.getAddNewText(), Strings.editDetailText);
        softAssert.assertEquals(addChannelPage.getNameLabelText(),Strings.nameText);
        softAssert.assertEquals(addChannelPage.getDescriptionLabelText(),Strings.descriptionText);
        softAssert.assertEquals(addChannelPage.getChannelNameFieldTextFromUpdatePage(),channelName);
        softAssert.assertEquals(addChannelPage.getChannelDescriptionFieldText(),channelDescription);
        updatedChannelDescription = channelDescription+"Updated";
        addChannelPage.emptyDescriptionField();
        addChannelPage.enterChannelDesc(updatedChannelDescription);
        addChannelPage.clickOnUpdateButton();
        addChannelPage.verifyDialogBoxMessage();
        if (!addChannelPage.verifyDialogBoxMessage().toLowerCase().contains("success")) {
            addChannelPage.clickDialogBoxOkButton();
            addChannelPage.clickSubmitButton();
            addChannelPage.verifyDialogBoxMessage();
            Assert.assertTrue(addChannelPage.verifyDialogBoxMessage().toLowerCase().contains("success"),"Update Channel Failed "+addChannelPage.getCheckerID());
        }
        assertThat(page.getByLabel("Breadcrumb")).containsText("Channel");
        CheckerID = addChannelPage.getCheckerID();
        checkerTest.splitCheckIdAndName(CheckerID);
        checkerTest.approveRecordFromChecker();
        addChannelPage.clickDialogBoxOkButton();
        assertThat(page.getByLabel("Breadcrumb")).containsText("Channel");
        //addChannelPage.enterChannelName(channelName);
        softAssert.assertAll();
    }

    @Test(priority = 5, groups = "positive")
    public void verifyUpdatedValueOnEditPage() {
        assertThat(page.getByLabel("Breadcrumb")).containsText("Channel");
        addChannelPage.clickOnSearchButton();
        addChannelPage.clickOnEditButton();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(addChannelPage.getAddNewText(), Strings.editDetailText);
        softAssert.assertEquals(addChannelPage.getNameLabelText(),Strings.nameText);
        softAssert.assertEquals(addChannelPage.getDescriptionLabelText(),Strings.descriptionText);
        softAssert.assertEquals(addChannelPage.getChannelNameFieldTextOnViewPage(),channelName);
        softAssert.assertEquals(addChannelPage.getChannelDescriptionFieldText(),updatedChannelDescription);
        addChannelPage.clickCancelButton();
        assertThat(page.getByLabel("Breadcrumb")).containsText("Channel");
        softAssert.assertAll();
    }
    @Test(priority = 6, groups = "positive")
    public void verifyUpdatedValueOnViewPage()  {
        assertThat(page.getByLabel("Breadcrumb")).containsText("Channel");
        addChannelPage.clickOnSearchButton();
        addChannelPage.clickOnViewButton();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(addChannelPage.getAddNewText(), Strings.viewDetailsText);
        softAssert.assertEquals(addChannelPage.getNameLabelText(),Strings.nameText);
        softAssert.assertEquals(addChannelPage.getDescriptionLabelText(),Strings.descriptionText);
        softAssert.assertEquals(addChannelPage.getChannelNameFieldTextOnViewPage(),channelName);
        softAssert.assertEquals(addChannelPage.getChannelDescFieldTextOnViewPage(),updatedChannelDescription);
        addChannelPage.clickOnOkButton();
        assertThat(page.getByLabel("Breadcrumb")).containsText("Channel");
        softAssert.assertAll();
    }

    @Test(priority = 7, groups = "positive")
    public void rejectingRecordFromCheckerAfterUpdating(){
        assertThat(page.getByLabel("Breadcrumb")).containsText("Channel");
        addChannelPage.enterChannelName(channelName);
        addChannelPage.clickOnSearchButton();
        addChannelPage.clickOnEditButton();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(addChannelPage.getAddNewText(), Strings.editDetailText);
        softAssert.assertEquals(addChannelPage.getNameLabelText(),Strings.nameText);
        softAssert.assertEquals(addChannelPage.getDescriptionLabelText(),Strings.descriptionText);
        softAssert.assertEquals(addChannelPage.getChannelNameFieldTextFromUpdatePage(),channelName);
        softAssert.assertEquals(addChannelPage.getChannelDescriptionFieldText(),updatedChannelDescription);
        updatedChannelDescription = channelDescription+"Updated+1";
        addChannelPage.emptyDescriptionField();
        addChannelPage.enterChannelDesc(updatedChannelDescription);
        addChannelPage.clickOnUpdateButton();
        addChannelPage.verifyDialogBoxMessage();
        if (!addChannelPage.verifyDialogBoxMessage().toLowerCase().contains("success")) {
            addChannelPage.clickDialogBoxOkButton();
            addChannelPage.clickSubmitButton();
            addChannelPage.verifyDialogBoxMessage();
            Assert.assertTrue(addChannelPage.verifyDialogBoxMessage().toLowerCase().contains("success"),"Update Channel Failed "+addChannelPage.getCheckerID());
        }
        assertThat(page.getByLabel("Breadcrumb")).containsText("Channel");
        CheckerID = addChannelPage.getCheckerID();
        checkerTest.splitCheckIdAndName(CheckerID);
        checkerTest.rejectRecordFromChecker();
        addChannelPage.clickDialogBoxOkButton();
        assertThat(page.getByLabel("Breadcrumb")).containsText("Channel");
        softAssert.assertAll();
    }

    @Test(priority = 8, groups = "positive")
    public void assignBackRecordFromCheckerAfterUpdating(){
        assertThat(page.getByLabel("Breadcrumb")).containsText("Channel");
        addChannelPage.enterChannelName(channelName);
        addChannelPage.clickOnSearchButton();
        addChannelPage.clickOnEditButton();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(addChannelPage.getAddNewText(), Strings.editDetailText);
        softAssert.assertEquals(addChannelPage.getNameLabelText(),Strings.nameText);
        softAssert.assertEquals(addChannelPage.getDescriptionLabelText(),Strings.descriptionText);
        softAssert.assertEquals(addChannelPage.getChannelNameFieldTextFromUpdatePage(),channelName);
        softAssert.assertEquals(addChannelPage.getChannelDescriptionFieldText(),updatedChannelDescription);
        updatedChannelDescription = channelDescription+"Updated+1";
        addChannelPage.emptyDescriptionField();
        addChannelPage.enterChannelDesc(updatedChannelDescription);
        addChannelPage.clickOnUpdateButton();
        addChannelPage.verifyDialogBoxMessage();
        if (!addChannelPage.verifyDialogBoxMessage().toLowerCase().contains("success")) {
            addChannelPage.clickDialogBoxOkButton();
            addChannelPage.clickSubmitButton();
            addChannelPage.verifyDialogBoxMessage();
            Assert.assertTrue(addChannelPage.verifyDialogBoxMessage().toLowerCase().contains("success"),"Update Channel Failed "+addChannelPage.getCheckerID());
        }
        assertThat(page.getByLabel("Breadcrumb")).containsText("Channel");
        CheckerID = addChannelPage.getCheckerID();
        checkerTest.splitCheckIdAndName(CheckerID);
        checkerTest.assignBackRecordFromChecker();
        addChannelPage.clickDialogBoxOkButton();
        assertThat(page.getByLabel("Breadcrumb")).containsText("Channel");
        softAssert.assertAll();
    }

    @Test(priority = 9, groups = "positive")
    public void verifyInactiveNewlyCreatedChannel(){
        assertThat(page.getByLabel("Breadcrumb")).containsText("Channel");
        addChannelPage.enterChannelName(channelName);
        addChannelPage.selectAssignBackFromDropDown();
        addChannelPage.clickOnSearchButton();
        addChannelPage.clickOnActiveInactiveButton();
        addChannelPage.verifyDialogBoxMessage();
        if (!addChannelPage.verifyDialogBoxMessage().toLowerCase().contains("success")) {
            addChannelPage.clickDialogBoxOkButton();
            addChannelPage.clickSubmitButton();
            addChannelPage.verifyDialogBoxMessage();
            Assert.assertTrue(addChannelPage.verifyDialogBoxMessage().toLowerCase().contains("success"),"Inactivating Channel Failed "+addChannelPage.getCheckerID());
        }
        CheckerID = addChannelPage.getCheckerID();
        checkerTest.splitCheckIdAndName(CheckerID);
        checkerTest.approveRecordFromChecker();
        addChannelPage.clickDialogBoxOkButton();
    }
    @Test(priority = 10, groups = "positive")
    public void verifyActivatingNewlyCreatedChannel(){
        assertThat(page.getByLabel("Breadcrumb")).containsText("Channel");
        addChannelPage.enterChannelName(channelName);
        addChannelPage.selectApproveFromDropDown();
        addChannelPage.clickOnSearchButton();
        addChannelPage.clickOnActiveInactiveButton();
        addChannelPage.verifyDialogBoxMessage();
        if (!addChannelPage.verifyDialogBoxMessage().toLowerCase().contains("success")) {
            addChannelPage.clickDialogBoxOkButton();
            addChannelPage.clickSubmitButton();
            addChannelPage.verifyDialogBoxMessage();
            Assert.assertTrue(addChannelPage.verifyDialogBoxMessage().toLowerCase().contains("success"),"Activating Channel Failed "+addChannelPage.getCheckerID());
        }
        CheckerID = addChannelPage.getCheckerID();
        checkerTest.splitCheckIdAndName(CheckerID);
        checkerTest.approveRecordFromChecker();
        addChannelPage.clickDialogBoxOkButton();
    }


}

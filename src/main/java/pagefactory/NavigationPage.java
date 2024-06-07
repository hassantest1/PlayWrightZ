package pagefactory;

import com.microsoft.playwright.Page;

public class NavigationPage {

    private final Page page;

    private final String sideMenuLocator = "(//button[@class='logo p-link'])[2]";
    private final String sideMenuLinkLocator = "(//button[@class='menu-pin p-link'])[2]";
    private final String moduleNavigationLocator = "//div[@class='layout-menu-wrapper layout-sidebar-active']//li//span[text()=";
    private final String subModuleNavigationLocator = "//div[@class='layout-menu-wrapper layout-sidebar-active']//li//ul//span[text()=";

    public NavigationPage(Page page){
        this.page = page;
    }

    public void navigateToChannel(){
        navigation("Channel","Create Channel");
    }
    public void navigateToESB(){
        navigation("ESB","ESB");
    }
    public void navigateToProductCatalog(){
        navigation("Product Catalog","Product Catalog");
    }
    public void navigateToChecker(){
        navigation("Administration","Request Checker");
    }

    public void navigateToDashBoard(){
        page.navigate("http://10.0.1.4/#/");
    }

    public void navigation(String module,String subModule){

        page.click(sideMenuLocator);
        page.click(sideMenuLinkLocator);
        page.click(moduleNavigationLocator+"'"+module+"'"+"]");
        page.click(subModuleNavigationLocator+"'"+subModule+"'"+"]");
    }

}

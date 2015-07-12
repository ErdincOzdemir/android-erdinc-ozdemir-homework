package it2015.sabanciuniv.edu.erdincozdemir.objects;

import java.util.List;

/**
 * Created by Erdinc on 12.07.2015.
 */
public class ServiceResponseObject<T> {

    private int serviceMessageCode;
    private String serviceMessageText;
    private List<T> responseItems;

    public ServiceResponseObject() {
    }

    public int getServiceMessageCode() {
        return serviceMessageCode;
    }

    public void setServiceMessageCode(int serviceMessageCode) {
        this.serviceMessageCode = serviceMessageCode;
    }

    public String getServiceMessageText() {
        return serviceMessageText;
    }

    public void setServiceMessageText(String serviceMessageText) {
        this.serviceMessageText = serviceMessageText;
    }

    public List<T> getResponseItems() {
        return responseItems;
    }

    public void setResponseItems(List<T> responseItems) {
        this.responseItems = responseItems;
    }
}

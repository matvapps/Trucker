package com.foora.perevozkadev.ui.add_order.route.model;

/**
 * Created by Alexander Matvienko on 13.12.2018.
 */
public class RouteItem {

//    private ChooseCityView chooseCityView;
    private Type type;

    public RouteItem(Type type) {
        this.type = type;
    }

//    public RouteItem(ChooseCityView chooseCityView, Type type) {
//        this.chooseCityView = chooseCityView;
//        this.type = type;
//    }

//    public ChooseCityView getChooseCityView() {
//        return chooseCityView;
//    }
//
//    public void setChooseCityView(ChooseCityView chooseCityView) {
//        this.chooseCityView = chooseCityView;
//    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        LOADING_PLACE(22),
        UNLOADING_PLACE(33);

        private int value;

        Type(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

    }
}

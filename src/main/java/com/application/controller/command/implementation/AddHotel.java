package com.application.controller.command.implementation;

import com.application.entity.Hotel;
import com.application.controller.command.Command;
import com.application.controller.command.RequestParameter;
import com.application.controller.command.SessionAttribute;
import com.application.controller.command.rout.Rout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class AddHotel implements Command {
    @Override
    public Rout execute(HttpServletRequest request, HttpServletResponse response) {
        Optional<Integer> adminId = Optional.ofNullable((Integer) request.getSession().getAttribute(SessionAttribute.USER_ID));
        Optional<String> name = Optional.ofNullable(request.getParameter(RequestParameter.NAME));
        Optional<String> stars = Optional.ofNullable(request.getParameter(RequestParameter.STARS));
        Optional<String []> typeFood = Optional.ofNullable(request.getParameterValues(RequestParameter.TYPE_FOOD));
        Optional<String []> typeAllocation = Optional.ofNullable(request.getParameterValues(RequestParameter.TYPE_ALLOCATION));
        Optional<String []> typeComfort = Optional.ofNullable(request.getParameterValues(RequestParameter.TYPE_COMFORT));
        Optional<String> priceSingle = Optional.ofNullable(request.getParameter(RequestParameter.PRICE_SINGLE));
        Optional<String> priceDouble = Optional.ofNullable(request.getParameter(RequestParameter.PRICE_DOUBLE));
        Optional<String> priceTriple = Optional.ofNullable(request.getParameter(RequestParameter.PRICE_TRIPLE));
        Optional<String> priceExtra = Optional.ofNullable(request.getParameter(RequestParameter.PRICE_EXTRA));
        Optional<String> priceChild = Optional.ofNullable(request.getParameter(RequestParameter.PRICE_CHILD));
        Optional<String> priceFamily = Optional.ofNullable(request.getParameter(RequestParameter.PRICE_FAMILY));
        Optional<String> priceLuxe = Optional.ofNullable(request.getParameter(RequestParameter.PRICE_LUXE));
        Optional<String> priceSuite = Optional.ofNullable(request.getParameter(RequestParameter.PRICE_SUITE));
        Optional<String> reward = Optional.ofNullable(request.getParameter(RequestParameter.REWARD));
        Optional<String []> facilities = Optional.ofNullable(request.getParameterValues(RequestParameter.FACILITIES));
        Optional<String> country = Optional.ofNullable(request.getParameter(RequestParameter.COUNTRY));
        Optional<String> city = Optional.ofNullable(request.getParameter(RequestParameter.CITY));
        Optional<String> house = Optional.ofNullable(request.getParameter(RequestParameter.HOUSE));
        Optional<String> building = Optional.ofNullable(request.getParameter(RequestParameter.BUILDING));

        Hotel hotel = new Hotel();
        hotel.setAdminId(adminId.get());
        hotel.setName(name.get());
        hotel.setStars(Integer.parseInt(stars.get()));
        hotel.getFacilities().setTypeFood(makeTypeSomething(typeFood.get()));
        hotel.getFacilities().setTypeAllocation(makeTypeSomething(typeAllocation.get()));
        hotel.getFacilities().setTypeComfort(makeTypeSomething(typeComfort.get()));
        hotel.getPrices().setPriceAllocationIntArr(makePriceRoom(priceSingle.get(),priceDouble.get(),priceTriple.get(),priceExtra.get(),priceChild.get()));

        return null;
    }

    private int makeTypeSomething(String [] typeSomething){
        int [] typeSomethingInt = new int[typeSomething.length];
        for (int i = 0; i < typeSomething.length-1; i++){
            typeSomethingInt[i] = Integer.parseInt(typeSomething[i]);
        }
        int resultType = 0;
        for (int typeInt : typeSomethingInt){
            resultType = resultType | typeInt;
        }
        return resultType;
    }

    private int [] makePriceRoom(String priceSingle, String priceDouble, String priceTriple, String priceExtra, String priceChild){
        int [] priceRoom = new int[5];
        priceRoom[0] = Integer.parseInt(priceChild);
        priceRoom[1] = Integer.parseInt(priceExtra);
        priceRoom[2] = Integer.parseInt(priceTriple);
        priceRoom[3] = Integer.parseInt(priceDouble);
        priceRoom[4] = Integer.parseInt(priceSingle);
        return priceRoom;
    }
}

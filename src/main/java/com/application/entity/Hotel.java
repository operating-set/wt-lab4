package com.application.entity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Hotel extends Entity {

    private final Facilities facilities;
    private final Prices prices;
    private final Address address;
    private int adminId;
    private String name;
    private int stars;

    public class Facilities {

        private int typeFood;
        private int typeAllocation;
        private int typeComfort;
        private boolean parking;
        private boolean wifi;
        private boolean pets;
        private boolean business;

        private Facilities() {
        }

        public int getTypeFood() {
            return typeFood;
        }

        public void setTypeFood(int typeFood) {
            this.typeFood = typeFood;
        }

        public String getStrValTypeFood(){
            return  makeStrTypeFood();
        }

        public int getTypeAllocation() {
            return typeAllocation;
        }

        public void setTypeAllocation(int typeAllocation) {
            this.typeAllocation = typeAllocation;
        }

        public String getStrValTypeAllocation(){
            return makeStrTypeAllocation();
        }

        public int getTypeComfort() {
            return typeComfort;
        }

        public void setTypeComfort(int typeComfort) {
            this.typeComfort = typeComfort;
        }

        public String getStrValTypeComfort(){
            return makeStrTypeComfort();
        }

        public boolean isParking() {
            return parking;
        }

        public void setParking(boolean parking) {
            this.parking = parking;
        }

        public boolean isWifi() {
            return wifi;
        }

        public void setWifi(boolean wifi) {
            this.wifi = wifi;
        }

        public boolean isPets() {
            return pets;
        }

        public void setPets(boolean pets) {
            this.pets = pets;
        }

        public boolean isBusiness() {
            return business;
        }

        public String getStrValAddFacilities(){
            return makeStrAddFacilities();
        }

        public void setBusiness(boolean business) {
            this.business = business;
        }

        private String makeStrTypeComfort(){
            StringBuffer typeComfortStr = new StringBuffer();
            if ((typeComfort & EntityConstant.TYPE_ALLOCATION_STANDARD) != 0){
                typeComfortStr.append("standard ");
            }
            if ((typeComfort & EntityConstant.TYPE_ALLOCATION_FAMILY) != 0){
                typeComfortStr.append("family ");
            }
            if ((typeComfort & EntityConstant.TYPE_ALLOCATION_LUXE) !=0){
                typeComfortStr.append("luxe ");
            }
            if ((typeComfort & EntityConstant.TYPE_ALLOCATION_SUITE) !=0){
                typeComfortStr.append("suite ");
            }
            return typeComfortStr.toString();
        }

        private String makeStrTypeFood(){
            StringBuffer typeFoodStr = new StringBuffer();
            if ((typeFood & EntityConstant.HOTEL_NO) != 0){
                typeFoodStr.append("no ");
            }
            if ((typeFood & EntityConstant.HOTEL_BREAKFAST) != 0){
                typeFoodStr.append("breakfast ");
            }
            if ((typeFood & EntityConstant.HOTEL_HALF_BOARD) !=0){
                typeFoodStr.append("half board ");
            }
            if ((typeFood & EntityConstant.HOTEL_FULL_BOARD) !=0){
                typeFoodStr.append("full board ");
            }
            if ((typeFood & EntityConstant.HOTEL_ALL_INCLUSIVE) !=0){
                typeFoodStr.append("all inclusive ");
            }
            return typeFoodStr.toString();
        }

        private String makeStrTypeAllocation(){
            StringBuffer typeAllocationStr = new StringBuffer();
            if ((typeAllocation & EntityConstant.HOTEL_SINGLE) != 0){
                typeAllocationStr.append("single ");
            }
            if ((typeAllocation & EntityConstant.HOTEL_DOUBLE) != 0){
                typeAllocationStr.append("double ");
            }
            if ((typeAllocation & EntityConstant.HOTEL_TRIPLE) !=0){
                typeAllocationStr.append("triple ");
            }
            if ((typeAllocation & EntityConstant.HOTEL_EXTRA) !=0){
                typeAllocationStr.append("extra ");
            }
            if ((typeAllocation & EntityConstant.HOTEL_CHILD) !=0){
                typeAllocationStr.append("child ");
            }
            return typeAllocationStr.toString();
        }

        private String makeStrAddFacilities(){
            StringBuffer addFacilitiesStr = new StringBuffer();
            if (parking){
                addFacilitiesStr.append("parking ");
            }
            if (wifi){
                addFacilitiesStr.append("wifi ");
            }
            if (pets){
                addFacilitiesStr.append("pets ");
            }
            if (business){
                addFacilitiesStr.append("business ");
            }
            return addFacilitiesStr.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Facilities that = (Facilities) o;
            return typeFood == that.typeFood && typeAllocation == that.typeAllocation && typeComfort == that.typeComfort && parking == that.parking && wifi == that.wifi && pets == that.pets && business == that.business;
        }

        @Override
        public int hashCode() {
            return Objects.hash(typeFood, typeAllocation, typeComfort, parking, wifi, pets, business);
        }

        @Override
        public String toString() {
            return "Facilities{" +
                    "typeFood=" + typeFood +
                    ", typeAllocation=" + typeAllocation +
                    ", typeComfort=" + typeComfort +
                    ", parking=" + parking +
                    ", wifi=" + wifi +
                    ", pets=" + pets +
                    ", business=" + business +
                    '}';
        }
    }

    public class Prices {
        private int[] priceAllocation = new int[5];
        private int[] priceComfort = new int[4];
        private int rewardTourOperator;

        private Prices() {
        }

        public int[] getPriceAllocationIntArr() {
            return priceAllocation;
        }

        public void setPriceAllocationIntArr(int[] priceRoom) {
            this.priceAllocation = priceRoom;
        }

        public String getPriceAllocation() {
            return priceRoomToStringValue(this.priceAllocation);
        }

        public void setPriceAllocation(String priceRoom) {
            this.priceAllocation = priceRoomToIntArr(priceRoom);
        }

        public int[] getPriceComfortIntArr() {
            return priceComfort;
        }

        public void setPriceComfortIntArr(int[] priceComfort) {
            this.priceComfort = priceComfort;
        }

        public String getPriceComfort() {
            return priceComfortToStringValue(priceComfort);
        }

        public void setPriceComfort(String priceComfort) {
            this.priceComfort = priceComfortToIntArr(priceComfort);
        }

        public String getStandartPrice() {
            return String.valueOf(priceAllocation[0]);
        }

        public int getRewardTourOperator() {
            return rewardTourOperator;
        }

        public void setRewardTourOperator(int rewardTourOperator) {
            this.rewardTourOperator = rewardTourOperator;
        }

        private int[] priceRoomToIntArr(String priceRooms) {
            int[] priceRoomArr = new int[5];
            priceRoomArr[0] = Integer.parseInt(priceRooms.substring(0, 5));
            priceRoomArr[1] = Integer.parseInt(priceRooms.substring(5, 10));
            priceRoomArr[2] = Integer.parseInt(priceRooms.substring(10, 15));
            priceRoomArr[3] = Integer.parseInt(priceRooms.substring(15, 20));
            priceRoomArr[4] = Integer.parseInt(priceRooms.substring(20, 25));
            return priceRoomArr;
        }

        private String priceRoomToStringValue(int[] priceRooms) {
            StringBuffer priceRoomsStr = new StringBuffer();
            StringBuffer priceOneRoomStr = new StringBuffer();
            for (int priceOneRoom : priceRooms) {
                priceOneRoomStr.append(priceOneRoom);
                while (priceOneRoomStr.length() != 5) {
                    priceOneRoomStr.insert(0, '0');
                }
                priceRoomsStr.append(priceOneRoomStr);
            }
            return priceRoomsStr.toString();
        }

        private int[] priceComfortToIntArr(String priceComfort) {
            int[] priceComfortArr = new int[4];
            priceComfortArr[0] = Integer.parseInt(priceComfort.substring(0, 5));
            priceComfortArr[1] = Integer.parseInt(priceComfort.substring(5, 10));
            priceComfortArr[2] = Integer.parseInt(priceComfort.substring(10, 15));
            return priceComfortArr;
        }

        private String priceComfortToStringValue(int[] priceComfort) {
            StringBuffer priceComfortStr = new StringBuffer();
            StringBuffer priceOneTypeComfortStr = new StringBuffer();
            for (int price : priceComfort) {
                priceOneTypeComfortStr.append(price);
                while (priceOneTypeComfortStr.length() != 5) {
                    priceOneTypeComfortStr.insert(0, '0');
                }
                priceComfortStr.append(priceOneTypeComfortStr);
            }
            return priceComfortStr.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Prices prices = (Prices) o;
            return rewardTourOperator == prices.rewardTourOperator && Arrays.equals(priceAllocation, prices.priceAllocation) && Arrays.equals(priceComfort, prices.priceComfort);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(rewardTourOperator);
            result = 31 * result + Arrays.hashCode(priceAllocation);
            result = 31 * result + Arrays.hashCode(priceComfort);
            return result;
        }

        @Override
        public String toString() {
            return "Prices{" +
                    "priceRoom=" + Arrays.toString(priceAllocation) +
                    ", priceComfort=" + Arrays.toString(priceComfort) +
                    ", rewardTourOperator=" + rewardTourOperator +
                    '}';
        }
    }

    public class Address {
        private String countryCode;
        private String countryName;
        private String city;
        private String street;
        private int house;
        private int building;

        private Address() {
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public int getHouse() {
            return house;
        }

        public void setHouse(int house) {
            this.house = house;
        }

        public int getBuilding() {
            return building;
        }

        public void setBuilding(int building) {
            this.building = building;
        }

        public String getFullAddress(){
            return countryName+" "+city+" "+street+" "+house+ (building == 0 ? "" : "/"+building);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Address address = (Address) o;
            return house == address.house && building == address.building && countryCode.equals(address.countryCode) && countryName.equals(address.countryName) && city.equals(address.city) && street.equals(address.street);
        }

        @Override
        public int hashCode() {
            return Objects.hash(countryCode, countryName, city, street, house, building);
        }
    }

    private List<Room> listRoom;

    public Hotel() {
        this.facilities = new Facilities();
        this.prices = new Prices();
        this.address = new Address();
    }

    public Facilities getFacilities() {
        return facilities;
    }

    public Prices getPrices() {
        return prices;
    }

    public Address getAddress() {
        return address;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public List<Room> getListRoom() {
        return listRoom;
    }

    public void setListRoom(List<Room> listRoom) {
        this.listRoom = listRoom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hotel)) return false;
        if (!super.equals(o)) return false;
        Hotel hotel = (Hotel) o;
        return adminId == hotel.adminId && stars == hotel.stars && facilities.equals(hotel.facilities) && prices.equals(hotel.prices) && address.equals(hotel.address) && name.equals(hotel.name) && listRoom.equals(hotel.listRoom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), facilities, prices, address, adminId, name, stars, listRoom);
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "facilities=" + facilities.toString() +
                ", prices=" + prices.toString() +
                ", address=" + address.toString() +
                ", adminId=" + adminId +
                ", name='" + name + '\'' +
                ", stars=" + stars +
                ", listRoom=" + listRoom +
                "} " + super.toString();
    }
}

package com.darkgolly.gps.utils;

public class AzimuthCalculator {

    /**
     * Вычисляет азимут между двумя координатами в градусах.
     *
     * @param lat1 Широта первой точки
     * @param lon1 Долгота первой точки
     * @param lat2 Широта второй точки
     * @param lon2 Долгота второй точки
     * @return Азимут (курс) в градусах
     */
    public static double calculateHeading(double lat1, double lon1, double lat2, double lon2) {
        // Преобразование градусов в радианы
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Разница долгот
        double deltaLon = lon2Rad - lon1Rad;

        // Расчет азимута
        double x = Math.sin(deltaLon) * Math.cos(lat2Rad);
        double y = Math.cos(lat1Rad) * Math.sin(lat2Rad) -
                Math.sin(lat1Rad) * Math.cos(lat2Rad) * Math.cos(deltaLon);

        // Угол в радианах
        double theta = Math.atan2(x, y);

        // Преобразование радиан в градусы и нормализация
        return (Math.toDegrees(theta) + 360) % 360;
    }
}

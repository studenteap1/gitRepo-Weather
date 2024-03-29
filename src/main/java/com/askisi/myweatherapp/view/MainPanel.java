package com.askisi.myweatherapp.view;

import Pojos.City;
import com.askisi.myweatherapp.repositories.RepoWeatherApi;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import com.askisi.myweatherapp.controller.CityRepo;
import com.askisi.myweatherapp.controller.FavoriteRepo;
import gsonModels.WeatherData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JFrame {
    public MainPanel() {
        
        CityRepo controller = new CityRepo();

        this.setTitle("WEATHER APP");
        RepoWeatherApi repoWeatherApi = new RepoWeatherApi();


        // Create a JPanel for the top section
        JPanel topPanel = new JPanel(new BorderLayout());
        setLocation(800, 200);
        setPreferredSize(new Dimension(500,400));

        // Create a JTextField and a JButton
        JTextField textField = new JTextField();
        JButton searchButton = new JButton("Search by City");
        JPanel gridPanel = new JPanel(new GridLayout(5, 1));
        searchButton.addActionListener(e -> {
            WeatherData weather = repoWeatherApi.getWeather(textField.getText().trim().strip());
            String cityName=weather.getNearest_area().get(0).getAreaName().get(0).getValue();
            controller.insertData(cityName);
            City city = controller.getCityByName(cityName);
            FavoriteRepo favoriteRepo=new FavoriteRepo();
            favoriteRepo.incrementOrInsertFavorite(city);
            WeatherView weatherView =new WeatherView(this,weather,city);
            weatherView.setVisible(true);
        });

        // Add the JTextField and JButton to the top panel
        topPanel.add(textField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        


        // Add buttons to the grid panel
            JButton gridButton = new JButton("Προβολή Λίστας Πόλεων");
            gridButton.addActionListener(e -> {
            CityDataPanel cityPanel=new CityDataPanel(this);
            cityPanel.setVisible(true);
        });
            gridPanel.add(gridButton);
           
            JButton gridButton3 = new JButton("Έξοδος");
            gridButton3.addActionListener((new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(EXIT_ON_CLOSE);
                }
            }));
            gridPanel.add(gridButton3);
//        }
           

        // Add the top panel and the grid panel to the frame
        this.add(topPanel, BorderLayout.NORTH);
        this.add(gridPanel, BorderLayout.CENTER);

        // Set frame properties
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
}

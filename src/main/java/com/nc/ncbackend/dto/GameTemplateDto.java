package com.nc.ncbackend.dto;

import com.nc.ncbackend.pojo.Game;
import com.nc.ncbackend.pojo.GameRating;


public class GameTemplateDto {

    private long id;
    private String name;
    private String description;
    private int maxPlayers;
    private String author;
    private String gameMode;
    private String getGameModeDescription;
    private float rating;

    public GameTemplateDto(){
    }

    public GameTemplateDto(Game game){
        parse(game);
    }

    public void parse(Game game){
        id = game.getId();
        name = game.getName();
        description = game.getDescription();
        maxPlayers = game.getMaxPlayers();
        author = game.getAuthor().getName();
        gameMode = game.getGameMode().getName();
        getGameModeDescription = game.getGameMode().getDescription();

        if(game.getRating().size() != 0){
            rating = 0;
            int ratingCount = 0;
            for(GameRating gameRating : game.getRating()){
                rating += gameRating.getValue();
                ratingCount += 1;
            }
            rating = rating / ratingCount;
        }


    }
}

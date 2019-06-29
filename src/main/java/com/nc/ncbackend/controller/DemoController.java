package com.nc.ncbackend.controller;


import com.nc.ncbackend.pojo.*;
import com.nc.ncbackend.repository.GameInstanceRepository;
import com.nc.ncbackend.repository.NotificationRepository;
import com.nc.ncbackend.repository.PhotoRepository;
import com.nc.ncbackend.service.DemoDataService;
import com.nc.ncbackend.service.GameInstanceService;
import com.nc.ncbackend.service.GamePointService;
import com.nc.ncbackend.service.GameService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class DemoController {

    @Autowired
    DemoDataService demoDataService;

    @Autowired
    GamePointService gamePointService;

    @Autowired
    GameService gameService;

    @Autowired
    GameInstanceService gameInstanceService;

    @Deprecated
    @Autowired
    GameInstanceRepository gameInstanceRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    PhotoRepository photoRepository;


    @RequestMapping("/")
    @ResponseBody
    public String hello(){
        return "Hello World";
    }

    @RequestMapping("/demodata/local")
    @ResponseBody
    public List<DemoData> getLocalDemoData(){
        return demoDataService.getLocalDemoData();
    }

    @RequestMapping("/demodata")
    @ResponseBody
    public List<DemoData> getDemoData(){
        return demoDataService.getDemoData();
    }

    @RequestMapping("/demodata/add")
    @ResponseBody
    public List<DemoData> postDemoData(
            @RequestParam(value="id", required=true) String id,
            @RequestParam(value="value", required=true) String value,
            @RequestParam(value="someDate", required=true) String someDate
    ){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        demoDataService.save(new DemoData(Integer.valueOf(id), value, LocalDate.parse(someDate, formatter)));
        return demoDataService.getDemoData();
    }

    @ResponseBody
    @RequestMapping(value = "/demodata/photo/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] testphoto(@PathVariable("id") long id) throws IOException {

        Photo photo = photoRepository.findOne(id);

//        Resource resource = new ClassPathResource("images/me.jpg");
//        InputStream in = resource.getInputStream();
//        byte[] data = IOUtils.toByteArray(in);

        return photo.getFile();
    }

    @ResponseBody
    @RequestMapping(value = "/demodata/photo", method = RequestMethod.POST)
    public ResponseEntity<Long> sendphoto(@RequestBody byte[] data) throws IOException {

        Photo photo = new Photo(data);
        photoRepository.save(photo);

        return new ResponseEntity<>(photo.getId(), HttpStatus.OK);
    }

    @GetMapping("/demo/template")
    @ResponseBody
    public List<Game> getGames(){

        return (List<Game>) gameService.findAll();
    }

    @GetMapping("/demo/instance")
    @ResponseBody
    public List<GameInstance> getGameInstances(){



        return gameInstanceService.findAll();
    }


    @GetMapping("/demo/init")
    @ResponseBody
    public String addGames(){

        gameInstanceService.deleteAll();
        gameService.deleteAll();

        // person -------------------

        Photo photo = null;
        Resource resource = new ClassPathResource("images/me.jpg");
        try(InputStream in = resource.getInputStream()) {
            byte[] data = IOUtils.toByteArray(in);
            photo = new Photo(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Person author = new Person("danya", "danya@mail.com", "pass");
        if(photo != null){
            author.setPhoto(photo);
        }

        Person dima = new Person("dima", "dima@mail.com", "pass");
        Person pers2 = new Person("danil2", "sobaka", "pass");


        // gamemode ------------------------

        GameMode soloMode = new GameMode("Соло", "Режим для одного игрока");
        GameMode commonMode = new GameMode("Каждый за себя", "Стандартный режим игры");

        // game -------------------------------

        GamePoint gamePoint1 = new GamePoint("Точка 1", "Описание точки 1", 23.123f, 32.163f);
        FindCondition findCondition1 = new FindCondition("Условие поиска точки 1");
        CheckCondition checkCondition1 = new CheckCondition(3, "Сделайте фотку" , null);
        Task task1 = new Task(gamePoint1, findCondition1, checkCondition1);

        GamePoint gamePoint2 = new GamePoint("Точка 2","Описание точки 2", 31, 11);
        FindCondition findCondition2 = new FindCondition("Условие поиска точки 2");
        CheckCondition checkCondition2 = new CheckCondition(1, "Просто дойдите" , null);
        Task task2 = new Task(gamePoint2, findCondition2, checkCondition2);

        GamePoint gamePoint3 = new GamePoint("Точка 3","Описание точки 3", 37, 14);
        FindCondition findCondition3 = new FindCondition("Условие поиска точки 3" );
        CheckCondition checkCondition3 = new CheckCondition(2, "Отправить код" , "Секрктный код 3");
        Task task3 = new Task(gamePoint3, findCondition3, checkCondition3);

        task2.getPrevTasks().add(task1);
        task3.getPrevTasks().add(task2);

        Game game = new Game("Прогулка от Горьковской до Кронвера", 0);
        game.getTasks().add(task1);
        game.getTasks().add(task2);
        game.getTasks().add(task3);

        game.setAuthor(author);
        game.setGameMode(soloMode);
        game.setDescription("Квест, для тестирования API соло игры");

        game.getRating().add(new GameRating(author, 7));
        game.getRating().add(new GameRating(dima, 9));
        game.getRating().add(new GameRating(pers2, 9));

        gameService.save(game);





        GamePoint gamePoint4 = new GamePoint("Точка 4","Описание точки 4", 31, 11);
        FindCondition findCondition4 = new FindCondition("Условие поиска точки 4");
        CheckCondition checkCondition4 = new CheckCondition(3, "Задание для точки 4" , null);
        Task task4 = new Task(gamePoint4, findCondition4, checkCondition4);

        GamePoint gamePoint5 = new GamePoint("Точка 5","Описание точки 5", 37, 14);
        FindCondition findCondition5 = new FindCondition("Условие поиска точки 5" );
        CheckCondition checkCondition5 = new CheckCondition(2, "Задание для точки 5" , "code5");
        Task task5 = new Task(gamePoint5, findCondition5, checkCondition5);

        task5.getPrevTasks().add(task4);


        Game game2 = new Game("Тестовая игра", 0);
        game2.getTasks().add(task4);
        game2.getTasks().add(task5);

        game2.setAuthor(author);
        game2.setGameMode(commonMode);
        game2.setDescription("квест для тестирования взаимодействия игроков");

        game2.getRating().add(new GameRating(author, 5));
        game2.getRating().add(new GameRating(dima, 4));

        gameService.save(game2);




        GamePoint gamePoint6 = new GamePoint("Точка 4","Описание точки 4", 31, 11);
        FindCondition findCondition6 = new FindCondition("Условие поиска точки 4");
        CheckCondition checkCondition6 = new CheckCondition(3, "Задание для точки 4" , null);
        Task task6 = new Task(gamePoint6, findCondition6, checkCondition6);

        Game game3 = new Game("Взятие бастилии", 0);
        game3.getTasks().add(task6);

        game3.setAuthor(dima);
        game3.setGameMode(commonMode);
        game3.setDescription("    Вам предстоит ыфв фывыф выф в ывф ыв фыв пкулпукщпл дьсм исми аи лапрдокешщ олит смибсмьита вот...");

        game3.getRating().add(new GameRating(author, 7));
        game3.getRating().add(new GameRating(dima, 5));

        gameService.save(game3);




        GamePoint gamePoint11 = new GamePoint("Точка 4","Описание точки 4", 31, 11);
        FindCondition findCondition11 = new FindCondition("Условие поиска точки 4");
        CheckCondition checkCondition11 = new CheckCondition(3, "Задание для точки 4" , null);
        Task task11 = new Task(gamePoint11, findCondition11, checkCondition11);

        Game game6 = new Game("Мародёрский налёт на пятерочку", 0);
        game6.getTasks().add(task11);

        game6.setAuthor(dima);
        game6.setGameMode(commonMode);
        game6.setDescription("    Вам предстоит ыфв фывыф выф в ывф ыв фыв пкулпукщпл дьсм исми аи лапрдокешщ олит смибсмьита вот...");

        game6.getRating().add(new GameRating(author, 7));
        game6.getRating().add(new GameRating(dima, 5));

        gameService.save(game6);




        GamePoint gamePoint12 = new GamePoint("Точка 4","Описание точки 4", 31, 11);
        FindCondition findCondition12 = new FindCondition("Условие поиска точки 4");
        CheckCondition checkCondition12 = new CheckCondition(3, "Задание для точки 4" , null);
        Task task12 = new Task(gamePoint12, findCondition12, checkCondition12);

        Game game7 = new Game("Проверка закладок в Московском районе", 0);
        game7.getTasks().add(task12);

        game7.setAuthor(dima);
        game7.setGameMode(commonMode);
        game7.setDescription("    Вам предстоит ыфв фывыф выф в ывф ыв фыв пкулпукщпл дьсм исми аи лапрдокешщ олит смибсмьита вот...");

        game7.getRating().add(new GameRating(author, 3));
        game7.getRating().add(new GameRating(dima, 5));

        gameService.save(game7);




        GamePoint gamePoint7 = new GamePoint("Первый перекрёсток","Квест начинается с перекрёстка среднего проспекта и 17 линии", 1, 1);
        FindCondition findCondition7 = new FindCondition("Расположение точки отмечено на вашей карте");
        CheckCondition checkCondition7 = new CheckCondition(1, "Остановитесь возле пешеходного перехода" , null);
        Task task7 = new Task(gamePoint7, findCondition7, checkCondition7);

        GamePoint gamePoint8 = new GamePoint("Перекрёсток 'Дикси'","Второй перекрёсток на пути к метро 'Василеостровская'", 1, 1);
        FindCondition findCondition8 = new FindCondition("Двигайтесь на восток по Среднему проспекту до следующего перекрёстка");
        CheckCondition checkCondition8 = new CheckCondition(2, "Подойдите к высокому железному столбу на другой стороне. Какое количество бетонных блоков его удерживает?" , "10");
        Task task8 = new Task(gamePoint8, findCondition8, checkCondition8);

        GamePoint gamePoint9 = new GamePoint("Очередной столб","Здесь еще много таких столбов. Давайте наёдем следующий.", 1, 1);
        FindCondition findCondition9 = new FindCondition("Продолжайте движение на восток до следующего подобного столба.");
        CheckCondition checkCondition9 = new CheckCondition(3, "Подойдите к столбу и сделайте фото здания противоположном угле перекрёстка." , null);
        Task task9 = new Task(gamePoint9, findCondition9, checkCondition9);

//        GamePoint gamePoint10 = new GamePoint("Последний","Их тут еще много осталось. Но нам пора заканчивать. Следующий столб будет последним.", 59.942753f, 30.277770f);
//        FindCondition findCondition10 = new FindCondition("Продолжайте движение на восток до следующего подобного столба.");
//        CheckCondition checkCondition10 = new CheckCondition(3, "Подойдите к столбу и сделайте фото чего-нибудь." , null);
//        Task task10 = new Task(gamePoint10, findCondition10, checkCondition10);

        task8.getPrevTasks().add(task7);
        task9.getPrevTasks().add(task8);
        //task10.getPrevTasks().add(task9);

        Game game4 = new Game("На метрошку с крекера", 0);
        game4.getTasks().add(task7);
        game4.getTasks().add(task8);
        game4.getTasks().add(task9);
        //game4.getTasks().add(task10);

        game4.setAuthor(dima);
        game4.setGameMode(commonMode);
        game4.setDescription("    Квест для окончательного тестирования функционала приложения.");

        game4.getRating().add(new GameRating(author, 7));
        game4.getRating().add(new GameRating(dima, 5));

        gameService.save(game4);

        return "ok";
    }


}

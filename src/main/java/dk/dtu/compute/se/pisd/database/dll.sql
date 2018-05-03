drop database matadordemo;
create database MatadorDemo;
use MatadorDemo;

CREATE TABLE game(
gameID int(3) NOT NULL UNIQUE auto_increment,
gameName varchar(30) default ' ',
gameStatus ENUM('active','passive') default 'active',
currentPlayer int(1),
PRIMARY KEY(gameID)
);

CREATE TABLE player(
playerID int(3) NOT NULL,
gameID int(3) NOT NULL,
playerName varchar(10) NOT NULL,
hasLost boolean default false,
balance int(7) NOT NULL default 30000,
inJail boolean default false,
jailCard int(1) default 0,
jailTime int(1) not null default 0,
PRIMARY KEY(gameID, playerID),
FOREIGN KEY(gameID) REFERENCES game(gameID)
);

alter table game add foreign key(gameID, currentPlayer) references player(gameID, playerID);

CREATE TABLE car(
carColor INT(10) not null,
position int(2) default 0 check(position between 0 and 39),
playerID int(3) not null,
gameID int(3) not null,
PRIMARY KEY(gameID, carColor),
FOREIGN KEY (gameID, playerID) references player(gameID, playerID),
foreign key (gameID) references game(gameID)
);

CREATE TABLE properties(
spaceNumber int(2) check(spaceNumber between 0 and 39),
ownerP int(3) default null,
houses int(2) default 0 check(houses between 0 and 5),
Mortagaged boolean default false,
gameID int(3) not null,
PRIMARY KEY(gameID, spaceNumber),
FOREIGN KEY(gameID, ownerP) REFERENCES player(gameID, playerID),
foreign key(gameID) references game(gameID)
);

delimiter //
create procedure update_player
(in has_Lost tinyint(1),
in balance_ int(7),
in in_Jail tinyint(1),
in jail_Card int(1),
in jail_Time int(1),
in game_ID int(3),
in player_ID int (3))
begin 
update player 
set hasLost = has_lost,
balance = balance_,
inJail = in_Jail,
jailCard = jail_card,
jailTime = jail_Time
where
gameID = game_ID and playerID = player_ID;
end; //

delimiter //
create procedure update_properties
(in player_ID int(3),
in Mortagaged_ tinyint(1),
in game_ID tinyint(1),
in space_Number int(1))
begin 
update properties 
set ownerP = player_ID,
Mortagaged = Mortagaged_
where
gameID = game_ID and spaceNumber = space_Number;
end; //

delimiter ;
create view game_info as
select player.gameID as 'gameID', count(player.playerID) as 'number of players',
game.gameName as 'GameName'
from player natural join game
group by gameID;



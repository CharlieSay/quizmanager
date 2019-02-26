CREATE SCHEMA Webbi;

Use Webbi;

CREATE TABLE `QuestionBank` (
                              `id` INT NOT NULL AUTO_INCREMENT,
                              `question_text` TEXT NOT NULL,
                              `quiz_id` INT NOT NULL,
                              PRIMARY KEY (`id`)
);

CREATE TABLE `OptionBank` (
                            `id` INT NOT NULL AUTO_INCREMENT,
                            `option_text` TEXT NOT NULL,
                            `question_id` INT NOT NULL,
                            `correct_answer` BOOLEAN NOT NULL,
                            PRIMARY KEY (`id`)
);

CREATE TABLE `QuizBank` (
                          `id` INT NOT NULL AUTO_INCREMENT,
                          `title` TEXT NOT NULL,
                          `description` TEXT NOT NULL,
                          `creation_date` DATE NOT NULL,
                          `creator_id` INT NOT NULL,
                          PRIMARY KEY (`id`)
);

CREATE TABLE `Users` (
                       `id` INT NOT NULL AUTO_INCREMENT,
                       `username` VARCHAR(255) NOT NULL,
                       `passhash` VARCHAR(255) NOT NULL,
                       `group_id` INT NOT NULL,
                       PRIMARY KEY (`id`)
);

CREATE TABLE `UserGroups` (
                            `id` INT NOT NULL AUTO_INCREMENT,
                            `name` VARCHAR(255) NOT NULL,
                            PRIMARY KEY (`id`)
);

ALTER TABLE `QuestionBank` ADD CONSTRAINT `QuestionBank_fk0` FOREIGN KEY (`quiz_id`) REFERENCES `QuizBank`(`id`);

ALTER TABLE `OptionBank` ADD CONSTRAINT `OptionBank_fk0` FOREIGN KEY (`question_id`) REFERENCES `QuestionBank`(`id`);

ALTER TABLE `QuizBank` ADD CONSTRAINT `QuizBank_fk0` FOREIGN KEY (`creator_id`) REFERENCES `Users`(`id`);

ALTER TABLE `Users` ADD CONSTRAINT `Users_fk0` FOREIGN KEY (`group_id`) REFERENCES `UserGroups`(`id`);

INSERT INTO UserGroups(name) VALUES ('Edit'),('View'),('Restricted');

INSERT INTO Users (username, passhash, group_id) VALUE ('charlie.say',SHA1('password123'),1);
INSERT INTO Users (username, passhash, group_id) VALUE ('alec.tunbridge',SHA1('123456789'),2);
INSERT INTO Users (username, passhash, group_id) VALUE ('tom.higgins',SHA1('bob'),3);


SELECT * FROM QuizBank;

INSERT INTO QuizBank (title, description, creation_date, creator_id) VALUES ('Romantic Era Music','1800-1850',CURRENT_DATE,1);
INSERT INTO QuizBank (title, description, creation_date, creator_id) VALUES ('The Rise and Fall of Communism','History test ranging from Lenin to Stalin',CURRENT_DATE,1);
INSERT INTO QuizBank (title, description, creation_date, creator_id) VALUES ('Of Mice and Men','The Great Book by John Steinbeck',CURRENT_DATE,1);
INSERT INTO QuizBank (title, description, creation_date, creator_id) VALUES ('Pythagoras Theorem','Triangles, Triangles, Triangles.',CURRENT_DATE,1);
INSERT INTO QuizBank (title, description, creation_date, creator_id) VALUES ('Planets and Stars','Pluto, Earth, Mercury.',CURRENT_DATE,1);
INSERT INTO QuizBank (title, description, creation_date, creator_id) VALUES ('Foundations of England','God Save The Queen',CURRENT_DATE,1);

INSERT INTO QuestionBank(question_text, quiz_id) VALUES
                                                                           ('Was Chopin from the Romantic Era?',1),
                                                                           ('What year range was the Romantic Era?',1),
                                                                           ('Who wrote the famous Preface to the Lyri­cal Ballads?',1),
                                                                           ('What is Mozart called?',1),
                                                                           ('Who Founded Communism?',2),
                                                                           ('When did the Berlin Wall Crumble?',2),
                                                                           ('When was Stalin Born?',2),
                                                                           ('Lenny one of the Main Characters, what is age?',3),
                                                                           ('What colour was Candies Hand?',3),
                                                                           ('When was it set?',3),
                                                                           ('The Good Ol *WORD* dream?',3),
                                                                           ('Lennie offers to live in a cave, what is Georges response?',3),
                                                                           ('How does it even work?',4),
                                                                           ('What shape is it based off?',4),
                                                                           ('Sides are is X, 7 and 3. What is X?',4),
                                                                           ('Smallest Planet?',5),
                                                                           ('How big was the empire?',5),
                                                                           ('Where did the word England come from?',6),
                                                                           ('How cold is manchester on average?',6),
                                                                           ('Is it coming home?',6);

INSERT INTO OptionBank(option_text, question_id, correct_answer) VALUES
                                                                        ('Yes',1,true),
                                                                        ('No',1,False),
                                                                        ('Maybe',1,False),
                                                                        ('1000-1100',2,False),
                                                                        ('1100-1200',2,False),
                                                                        ('1200-1300',2,False),
                                                                        ('1780-1910',2,True),
                                                                        ('William Woodsworth',3,True),
                                                                        ('Steve Coogan',3,false),
                                                                        ('Bob Dylan',3,false),
                                                                        ('Jeremy Kyle',3,false),
                                                                        ('Richard Hammond',3,false),
                                                                        ('Richard',4,false),
                                                                        ('Wolfgang',4,True),
                                                                        ('Victor d Hupay',5,true),
                                                                        ('Joesph Stalin',5,false),
                                                                        ('1000',6,false),
                                                                        ('1982',6,false),
                                                                        ('1988',6,false),
                                                                        ('1989',6,true),
                                                                        ('1878',7,true),
                                                                        ('1890',7,false),
                                                                        ('1860',7,false),
                                                                        ('50',8,false),
                                                                        ('60',8,false),
                                                                        ('20',8,true),
                                                                        ('30',8,false),
                                                                        ('Does not have a hand',9,true),
                                                                        ('Red',9,false),
                                                                        ('Purple',9,false),
                                                                        ('1910',10,true),
                                                                        ('1920',10,false),
                                                                        ('1905',10,false),
                                                                        ('1900',10,false),
                                                                        ('American',11,true),
                                                                        ('British',11,false),
                                                                        ('Canadian',11,false),
                                                                        ('No',12,true),
                                                                        ('Yes',12,false),
                                                                        ('When we have made our dream come true',12,false),
                                                                        ('Use two sides to calculate the third',13,true),
                                                                        ('You put you finger on X and say there it is',13,false),
                                                                        ('You just miss it because you do not understand it',13,false),
                                                                        ('Circle',14,false),
                                                                        ('Icosahedron',14,false),
                                                                        ('Triangle',14,true),
                                                                        ('A Bell',14,false),
                                                                        ('7.62',15,true),
                                                                        ('73',15,false),
                                                                        ('10',15,false),
                                                                        ('Pluto',16,true),
                                                                        ('Uranus',16,false),
                                                                        ('YV Canis Majori',16,false),
                                                                        ('Hippocamp',16,false),
                                                                        ('Big',17,false),
                                                                        ('Super Big',17,true),
                                                                        ('England had an empire?',17,false),
                                                                        ('Briton usage',18,false),
                                                                        ('Tribal german language clash',18,false),
                                                                        ('Genie from Alladin came and said so',18,false),
                                                                        ('Angli Latin, Engle',18,true),
                                                                        ('Fishermen in the great north',18,false),
                                                                        ('-10c',19,false),
                                                                        ('-20c',19,false),
                                                                        ('0c',19,false),
                                                                        ('10c',19,true),
                                                                        ('Yes',20,false),
                                                                        ('Of Course',20,false),
                                                                        ('Bring on 2020',20,false),
                                                                        ('Its surely is, coming home',20,false);

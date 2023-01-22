-- Creación de estadísticas.
INSERT INTO statistic(gold, glory, num_orcs_killed, num_war_lord_killed, num_won_games, num_played_games, time_played, num_players, damage_dealt)
VALUES (0, 0, 0, 0, 0, 0, 0, null, 0),
       (0, 0, 0, 0, 0, 0, 0, null, 0),
       (0, 0, 0, 0, 0, 0, 0, null, 0),
       (0, 0, 0, 0, 0, 0, 0, null, 0),
       (0, 0, 0, 0, 0, 0, 0, null, 0),
       (0, 0, 0, 0, 0, 0, 0, null, 0),
       (0, 0, 0, 0, 0, 0, 0, null, 0),
       (0, 0, 0, 0, 0, 0, 0, null, 0),
       (0, 0, 0, 0, 0, 0, 0, null, 0),
       (0, 0, 0, 0, 0, 0, 0, null, 0);

-- Creación de usuarios.
INSERT INTO users(username, password, avatar, tier, description, authority, birth_date, enable, is_connected, statistic_id)
VALUES ('alesanfe', '$2a$10$Qag.ujgwDcKY3gBsY08/L.ZeH.BUplw9pjCdgFT7TR3B9Y0FgxPIG', 'https://i.pinimg.com/736x/bd/33/43/bd3343e3e4e13c58e408c79f0e029b75.jpg', 0,
        'I am a description', 'DOKTOL', '1999-02-01', 1, 0, 1),
        ('antonio', '$2a$10$Qag.ujgwDcKY3gBsY08/L.ZeH.BUplw9pjCdgFT7TR3B9Y0FgxPIG', 'https://i.pinimg.com/736x/bd/33/43/bd3343e3e4e13c58e408c79f0e029b75.jpg', 0,
        'I am a description', 'USER', '1999-02-01', 1, 0, 2),
       ('laurolmer', '$2a$10$Qag.ujgwDcKY3gBsY08/L.ZeH.BUplw9pjCdgFT7TR3B9Y0FgxPIG', 'https://i.pinimg.com/736x/bd/33/43/bd3343e3e4e13c58e408c79f0e029b75.jpg', 0,
        'awanabumbambam', 'DOKTOL', '2002-08-21', 1, 0, 3),
       ('alvhidrod', '$2a$10$Qag.ujgwDcKY3gBsY08/L.ZeH.BUplw9pjCdgFT7TR3B9Y0FgxPIG', 'https://i.pinimg.com/736x/bd/33/43/bd3343e3e4e13c58e408c79f0e029b75.jpg', 0,
        'drakorion', 'USER', '2002-02-23', 1, 0, 4),
       ('ismruijur', '$2a$10$Qag.ujgwDcKY3gBsY08/L.ZeH.BUplw9pjCdgFT7TR3B9Y0FgxPIG', 'https://i.pinimg.com/736x/bd/33/43/bd3343e3e4e13c58e408c79f0e029b75.jpg', 0,
        'er jefe brrr', 'USER', '2002-10-27', 1, 0, 5),
       ('ivasansan1', '$2a$10$Qag.ujgwDcKY3gBsY08/L.ZeH.BUplw9pjCdgFT7TR3B9Y0FgxPIG', 'https://i.pinimg.com/736x/bd/33/43/bd3343e3e4e13c58e408c79f0e029b75.jpg', 0,
        'bético encubierto', 'DOKTOL', '2002-11-12', 1, 0, 6),
       ('pedruiagu', '$2a$10$Qag.ujgwDcKY3gBsY08/L.ZeH.BUplw9pjCdgFT7TR3B9Y0FgxPIG', 'https://i.pinimg.com/736x/bd/33/43/bd3343e3e4e13c58e408c79f0e029b75.jpg', 0,
        'sácame del bolsillo', 'DOKTOL', '2002-10-01', 1, 0, 7),
        ('lapaqui', '$2a$10$Qag.ujgwDcKY3gBsY08/L.ZeH.BUplw9pjCdgFT7TR3B9Y0FgxPIG', 'https://i.pinimg.com/736x/bd/33/43/bd3343e3e4e13c58e408c79f0e029b75.jpg', 0,
        'vivo en una simulación', 'USER', '2003-05-11', 1, 0, 8),
        ('pepe', '$2a$10$Qag.ujgwDcKY3gBsY08/L.ZeH.BUplw9pjCdgFT7TR3B9Y0FgxPIG', 'https://i.pinimg.com/736x/bd/33/43/bd3343e3e4e13c58e408c79f0e029b75.jpg', 0,
        'pepito de carne mechá', 'USER', '2004-07-14', 1, 0, 9);

-- Creación de mensajes.
INSERT INTO messages(content, time, receiver_id, sender_id, read, type)
VALUES ('Hola, soy Alesanfe', '2020-02-01 12:00', 1, 2, 1, 'CHAT'),
       ('Hola, soy Antonio', '2020-02-01 12:00', 2, 1, 1, 'CHAT');

-- Creación de capacidades.
INSERT INTO capacities(state_capacity, less_damage)
VALUES ('MELEE', false),
       ('RANGE', false),
       ('MAGIC', false),
       ('EXPERTISE', false),
       ('MELEE', true),
       ('RANGE', true),
       ('MAGIC', true),
       ('EXPERTISE', true);

-- Creación de héroes.
INSERT INTO heroes(name, front_image, health, role)
VALUES ('Valerys', '/resources/images/heroes/valerys_hero_front.png', 3, 'KNIGHT'),
       ('Lisavette', '/resources/images/heroes/lisavette_hero_back.png', 3, 'KNIGHT'),
       ('Beleth-Il', '/resources/images/heroes/belethil_hero_front.png', 3, 'EXPLORER'),
       ('Idril', '/resources/images/heroes/idril_hero_back.png', 3, 'EXPLORER'),
       ('Neddia', '/resources/images/heroes/neddia_hero_front.png', 2, 'THIEF'),
       ('Feldon', '/resources/images/heroes/feldon_hero_back.png', 2, 'THIEF'),
       ('Aranel', '/resources/images/heroes/aranel_hero_front.png', 2, 'WIZARD'),
       ('Taheral', '/resources/images/heroes/taheral_hero_back.png', 2, 'WIZARD');

-- Creación de habilidades.
INSERT INTO abilities(name, front_image, role, attack, quantity, path_name, enemy_is_needed)
VALUES ('Compañero Lobo', '/resources/images/abilities/wolf_abilities_front.png', 'EXPLORER', 2, 1, 'fellowWolf', 1),
       ('Disparo Certero', '/resources/images/abilities/sharp_abilities_front.png', 'EXPLORER', 3, 2, 'preciseShot', 1),
       ('Disparo Rápido', '/resources/images/abilities/rapid_abilities_front.png', 'EXPLORER', 1, 6, 'rapidFire', 1),
       ('En la Diana', '/resources/images/abilities/diana_abilities_front.png', 'EXPLORER', 4, 1, 'target', 1),
       ('Lluvia de flechas', '/resources/images/abilities/rain_abilities_front.png', 'EXPLORER', 2, 2, 'arrowRain', 1),
       ('Recoger flechas', '/resources/images/abilities/pick_abilites_front.png', 'EXPLORER', 0, 2, 'collectArrows', 0),
       ('Supervivencia', '/resources/images/abilities/survival_abilities_front.png', 'EXPLORER', 0, 1, 'survival', 1),
       ('Ataque Brutal', '/resources/images/abilities/brutal_abilities_front.png', 'KNIGHT', 3, 2, 'brutalAttack', 1),
       ('Carga con Escudo', '/resources/images/abilities/charge_abilities_front.png', 'KNIGHT', 2, 1, 'shieldCharge', 1),
       ('Doble Espadazo', '/resources/images/abilities/doble_abilities_front.png', 'KNIGHT', 2, 2, 'doubleSlash', 1),
       ('Escudo', '/resources/images/abilities/shield_abilities_front.png', 'KNIGHT', 0, 2, 'shield', 1),
       ('Espadazo', '/resources/images/abilities/sword_abilities_front.png', 'KNIGHT', 1, 4, 'slash', 1),
       ('Paso Atrás', '/resources/images/abilities/back_abilities_front.png', 'KNIGHT', 0, 2, 'stepBack', 0),
       ('Todo o Nada', '/resources/images/abilities/all_abilities_front.png', 'KNIGHT', 1, 1, 'allOrNothing', 1),
       ('Voz de Aliento', '/resources/images/abilities/AAAAAAAA_abilities_front.png', 'KNIGHT', 0, 1, 'voiceOfEncouragement', 0),
       ('Aura protectora', '/resources/images/abilities/auraprotectora_ability_front.png', 'WIZARD', 0, 1, 'protectiveAura', 0),
       ('Bola de fuego', '/resources/images/abilities/boladefuego_ability_front.png', 'WIZARD', 2, 1, 'fireball', 0),
       ('Disparo gélido', '/resources/images/abilities/disparogelido_ability_front.png', 'WIZARD', 1, 2, 'frostShot', 1),
       ('Flecha corrosiva', '/resources/images/abilities/flechacorrosiva_ability_front.png', 'WIZARD', 1, 1, 'corrosiveArrow', 1),
       ('Golpe de bastón', '/resources/images/abilities/golpebaston_ability_front.png', 'WIZARD', 1, 4, 'staffHit', 1),
       ('Orbe curativo', '/resources/images/abilities/orbecurativo_ability_front.png', 'WIZARD', 0, 1, 'healingOrb', 0),
       ('Proyectil ígneo', '/resources/images/abilities/proyectiligneo_ability_front.png', 'WIZARD', 2, 3, 'igneousProjectile', 1),
       ('Reconstitución', '/resources/images/abilities/reconstitucion_ability_front.png', 'WIZARD', 0, 1, 'reconstitution', 0),
       ('Torrente de luz', '/resources/images/abilities/torrentedeluz_ability_front.png', 'WIZARD', 2, 1, 'lightTorrent', 0),
       ('Al corazon', '/resources/images/abilities/alcorazon_ability_front.png', 'THIEF', 4, 2, 'toTheHeart', 1),
       ('Ataque furtivo', '/resources/images/abilities/ataquefurtivo_ability_front.png', 'THIEF', 2, 3, 'stealthAttack', 1),
       ('Ballesta precisa', '/resources/images/abilities/ballestaprecisa_ability_front.png', 'THIEF', 2, 3, 'preciseBow', 1),
       ('En las sombras', '/resources/images/abilities/enlassombras_ability_front.png', 'THIEF', 1, 2, 'inTheShadows', 1),
       ('Engañar', '/resources/images/abilities/enganar_ability_front.png', 'THIEF', 0, 1, 'deceive', 1),
       ('Robar bolsillo', '/resources/images/abilities/robarbolsillos_ability_front.png', 'THIEF', 0, 1, 'stealPockets', 0),
       ('Saqueo', '/resources/images/abilities/saqueo_ability_front.png', 'THIEF', 0, 1, 'loot1', 0),
       ('Saqueo2', '/resources/images/abilities/saqueo2_ability_front.png', 'THIEF', 0, 1, 'loot2', 0),
       ('Trampa', '/resources/images/abilities/trampa_ability_front.png', 'THIEF', 0, 1, 'trap', 1);

-- Creacion de orcos.
INSERT INTO enemies(name, front_image, health, not_hidden_glory, hidden_gold,
                    hidden_glory, has_cure, less_damage_wizard, is_night_lord)
VALUES ('Honda', '/resources/images/orcs/honda_orc_front.png', 2, 1, 1, 0, FALSE, FALSE, FALSE),
       ('Honda', '/resources/images/orcs/honda_orc_front.png', 2, 1, 1, 0, FALSE, FALSE, FALSE),
       ('Honda', '/resources/images/orcs/honda_orc_front.png', 2, 1, 0, 0, FALSE, FALSE, FALSE),
       ('Honda', '/resources/images/orcs/honda_orc_front.png', 2, 1, 0, 0, FALSE, FALSE, FALSE),
       ('Honda', '/resources/images/orcs/honda_orc_front.png', 2, 1, 0, 0, FALSE, FALSE, FALSE),
       ('Lanza', '/resources/images/orcs/lanza_orc_front.png', 3, 2, 1, 0, TRUE, FALSE, FALSE),
       ('Lanza', '/resources/images/orcs/lanza_orc_front.png', 3, 2, 1, 0, TRUE, FALSE, FALSE),
       ('Lanza', '/resources/images/orcs/lanza_orc_front.png', 3, 2, 0, 0, TRUE, FALSE, FALSE),
       ('Lanza', '/resources/images/orcs/lanza_orc_front.png', 3, 2, 0, 0, TRUE, FALSE, FALSE),
       ('Lanza', '/resources/images/orcs/chaman_orc_front.png', 3, 3, 0, 0, TRUE, FALSE, FALSE),
       ('Chaman', '/resources/images/orcs/chaman_orc_front.png', 3, 1, 2, 0, FALSE, TRUE, FALSE),
       ('Chaman', '/resources/images/orcs/chaman_orc_front.png', 3, 1, 1, 1, FALSE, TRUE, FALSE),
       ('Sword', '/resources/images/orcs/sword_orc_front.png', 4, 2, 1, 0, FALSE, FALSE, FALSE),
       ('Sword', '/resources/images/orcs/sword_orc_front.png', 4, 2, 0, 0, FALSE, FALSE, FALSE),
       ('Sword', '/resources/images/orcs/sword_orc_front.png', 4, 2, 0, 0, FALSE, FALSE, FALSE),
       ('Sword', '/resources/images/orcs/bounty_orc_front.png', 4, 2, 1, 0, FALSE, FALSE, FALSE),
       ('Sword', '/resources/images/orcs/bounty_orc_front.png', 4, 2, 1, 0, FALSE, FALSE, FALSE),
       ('Sword', '/resources/images/orcs/bounty_orc_front.png', 4, 2, 2, 0, FALSE, FALSE, FALSE),
       ('Sword', '/resources/images/orcs/bounty_orc_front.png', 4, 3, 2, 1, FALSE, FALSE, FALSE),
       ('Sword', '/resources/images/orcs/bounty_orc_front.png', 4, 3, 2, 1, FALSE, FALSE, FALSE),
       ('Gran Chaman', '/resources/images/orcs/greatchaman_orc_front.png', 5, 3, 0, 0, FALSE, TRUE, FALSE),
       ('Gran Chaman', '/resources/images/orcs/greatchaman_orc_front.png', 5, 3, 2, 0, FALSE, TRUE, FALSE),
       ('Gran Chaman', '/resources/images/orcs/greatchaman_orc_front.png', 5, 3, 2, 0, FALSE, TRUE, FALSE),
       ('Gran Chaman', '/resources/images/orcs/greatchaman_orc_front.png', 5, 4, 2, 1, FALSE, TRUE, FALSE),
       ('Terminator', '/resources/images/orcs/axe_orc_front.png', 6, 4, 0, 0, FALSE, FALSE, FALSE),
       ('Terminator', '/resources/images/orcs/axe_orc_front.png', 6, 4, 0, 0, FALSE, FALSE, FALSE),
       ('Terminator', '/resources/images/orcs/axe_orc_front.png', 6, 4, 0, 0, FALSE, FALSE, FALSE),
       ('Gurdrug', '/resources/images/nightlords/gur_nightlord_front.png', 8, 0, 0, 0, FALSE, FALSE, TRUE),
       ('Roghkiller', '/resources/images/nightlords/rogh_nightlord_front.png', 9, 0, 0, 0, FALSE, FALSE, TRUE),
       ('Shriekknifer', '/resources/images/nightlords/shriek_nightlord_front.png', 10, 0, 0, 0, FALSE, FALSE, TRUE);

-- Productos
INSERT INTO products(name, front_image, price, attack, quantity, path_name)
VALUES ('Daga élfica', '/resources/images/products/dagaelfica_product_front.png', 3, 2, 2, 'elfDagger'),
       ('Poción curativa', '/resources/images/products/pocioncurativa_product_front.png', 8, 0, 3, 'healingPotion'),
       ('Piedra de amolar', '/resources/images/products/piedraamolar_product_front.png', 4, 0, 1, 'sharpeningStone'),
       ('Vial de conjuración', '/resources/images/products/vialconjuracion_product_front.png', 5, 0, 2, 'conjureVial'),
       ('Elixir de concentración', '/resources/images/products/pocioncurativa_product_front.png', 3, 0, 2, 'concentrationElixir'),
       ('Capa élfica', '/resources/images/products/capaelfica_product_front.png', 3, 0, 1, 'elfCloak'),
       ('Armadura de placas', '/resources/images/products/armaduraplacas_product_front.png', 4, 0, 1, 'plateArmor'),
       ('Alabarda orca', '/resources/images/products/alabardaorca_product_front.png', 5, 4, 1, 'orcaLance'),
       ('Arco compuesto', '/resources/images/products/arcocompuesto_product_front.png', 5, 4, 1, 'compoundBow');


-- Creación de la relación entre capacidades y productos.
INSERT INTO products_capacity(product_id, capacity_id)
VALUES (3, 1),
       (3, 2),
       (3, 2),
       (6, 3),
       (6, 4),
       (7, 1),
       (8, 1),
       (9, 2);

-- Creación de logros.
INSERT INTO achievements(name, description, image, threshold, achievement_type)
VALUES ('Peleador novato', 'Se le otorga un logro al usuario cuando inflije 10 de daño en total. ', 'awanakimkum', '10',4),
       ('Peleador de barrio', 'Se le otorga un logro al usuario cuando inflije 40 de daño en total. ', 'awanakimkum', '40',4),
       ('Bruce Lee', 'Se le otorga un logro al usuario cuando inflije 100 de daño en total. ', 'awanakimkum', '100',4),
       ('MariconaUwU', 'Se le otorga un logro al usuario cuando inflije 200 de daño en total. ', 'awanakimkum', '200',4),

       ('Aficionado', 'Se le otorga un logro al usuario cuando mata a 10 orcos. ', 'awanakimkum', '10',5),
       ('Veterano', 'Se le otorga un logro al usuario cuando mata a 50 orcos. ', 'awanakimkum', '50',5),
       ('Maestro', 'Se le otorga un logro al usuario cuando mata a 100 orcos. ', 'awanakimkum', '100',5),
       ('Asesino', 'Se le otorga un logro al usuario cuando mata a 200 orcos. ', 'awanakimkum', '200',5),
       ('Mataorcos', 'Se le otorga un logro al usuario cuando mata a 500 orcos. ', 'awanakimkum', '500',5),
       ('Exterminador', 'Se le otorga un logro al usuario cuando mata a 1000 orcos. ', 'awanakimkum', '1000',5),

       ('Money', 'Se le otorga un logro al usuario cuando tiene 10 de oro. ', 'awanakimkum', '10',0),
       ('Economista', 'Se le otorga un logro al usuario cuando tiene 20 de oro. ', 'awanakimkum', '20',0),
       ('Catalan', 'Se le otorga un logro al usuario cuando tiene 50 de oro. ', 'awanakimkum', '50',0),
       ('Elon Musk', 'Se le otorga un logro al usuario cuando tiene 100 de oro. ', 'awanakimkum', '10',0),
       ('Tio Gilito', 'Se le otorga un logro al usuario cuando tiene 1000 de oro. ', 'awanakimkum', '1000',0);

-- Habilidades de los heroes
INSERT INTO heroes_abilities(hero_id, abilities_id)
VALUES (1, 8),
       (1, 9),
       (1, 10),
       (1, 11),
       (1, 12),
       (1, 13),
       (1, 14),
       (1, 15),
       (2, 8),
       (2, 9),
       (2, 10),
       (2, 11),
       (2, 12),
       (2, 13),
       (2, 14),
       (2, 15),
       (3, 1),
       (3, 2),
       (3, 3),
       (3, 4),
       (3, 5),
       (3, 6),
       (3, 7),
       (4, 1),
       (4, 2),
       (4, 3),
       (4, 4),
       (4, 5),
       (4, 6),
       (4, 7),
       (5, 16),
       (5, 17),
       (5, 18),
       (5, 19),
       (5, 20),
       (5, 21),
       (5, 22),
       (5, 23),
       (5, 24),
       (6, 16),
       (6, 17),
       (6, 18),
       (6, 19),
       (6, 20),
       (6, 21),
       (6, 22),
       (6, 23),
       (6, 24),
       (7, 25),
       (7, 26),
       (7, 27),
       (7, 28),
       (7, 29),
       (7, 30),
       (7, 31),
       (7, 32),
       (8, 25),
       (8, 26),
       (8, 27),
       (8, 28),
       (8, 29),
       (8, 30),
       (8, 31),
       (8, 32);

-- Capacidades de los heroes
INSERT INTO heroes_capacities(hero_id, capacities_id)
VALUES (1, 1),
       (2, 1),
       (3, 2),
       (3, 5),
       (4, 2),
       (4, 5),
       (5, 4),
       (5, 6),
       (6, 4),
       (6, 6),
       (7, 3),
       (8, 3);

INSERT INTO users_friends(user_id, friends_id)
VALUES (1, 2),
       (2, 1);


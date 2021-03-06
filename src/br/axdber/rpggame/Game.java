package br.axdber.rpggame;

import br.axdber.rpggame.enums.*;

import java.util.*;

public class Game {


    private HashMap<String, Weapon> weaponHashMap;
    private HashMap<String, Enemy> enemyHashMap;
    private HashMap<String, Armor> armorHashMap;
    private DifficultyType difficultyType;
    static Scanner in = new Scanner(System.in);


    public Game(DifficultyType difficultyType) {
        this.weaponHashMap = new HashMap<>();
        this.armorHashMap = new HashMap<>();
        this.enemyHashMap = new HashMap<>();
        this.difficultyType = difficultyType;
        generateArmors();
        generateWeapons();
        generateEnemies();
    }

    public void generateWeapons() {
        weaponHashMap.put(WeaponType.BOW.name, new Weapon(WeaponType.BOW));
        weaponHashMap.put(WeaponType.CROSSBOW.name, new Weapon(WeaponType.CROSSBOW));
        weaponHashMap.put(WeaponType.AXE.name, new Weapon(WeaponType.AXE));
        weaponHashMap.put(WeaponType.SWORD.name, new Weapon(WeaponType.SWORD));
        weaponHashMap.put(WeaponType.STAFF.name, new Weapon(WeaponType.STAFF));
        weaponHashMap.put(WeaponType.WAND.name, new Weapon(WeaponType.WAND));
        weaponHashMap.put(WeaponType.HANDS.name, new Weapon(WeaponType.HANDS));

    }

    public void generateArmors() {
        armorHashMap.put(ArmorType.LEATHER.name, new Armor( ArmorType.LEATHER));
        armorHashMap.put(ArmorType.MAIL.name, new Armor(ArmorType.MAIL));
        armorHashMap.put(ArmorType.PLATE.name, new Armor( ArmorType.PLATE));
        armorHashMap.put(ArmorType.CLOTH.name, new Armor( ArmorType.CLOTH));
    }

    public void generateEnemies() {
        enemyHashMap.put("Armeiro", new Enemy(
                "Armeiro",
                SexType.MALE,
                new Warrior(),
                weaponHashMap.get(WeaponType.SWORD.name),
                armorHashMap.get(ArmorType.PLATE.name),
                0,
                difficultyType));
        enemyHashMap.put("Alquimista", new Enemy(
                "Alquimista",
                SexType.FEMALE,
                new Barbarian(),
                weaponHashMap.get(WeaponType.AXE.name),
                armorHashMap.get(ArmorType.LEATHER.name),
                1,
                difficultyType));
        enemyHashMap.put("L??der", new Enemy(
                "L??der",
                SexType.MALE,
                new Mage(),
                weaponHashMap.get(WeaponType.STAFF.name),
                armorHashMap.get(ArmorType.CLOTH.name),
                2,
                difficultyType));

    }

    public static int optionsLoop(int[] bounds, String[] msgs, String[] options) {
        int option = 0;
        while (option < bounds[0] || option > bounds[1]) {
            for (String msg : msgs) {
                System.out.println(msg);
            }
            for (String op : options) {
                System.out.println(op);
            }

            option = in.nextInt();
            if (option < bounds[0] || option > bounds[1]) {
                System.out.println("Op????o inv??lida");
            }
        }
        return option;
    }

    public void setTimeout(long timeout) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("...");
            }
        }, timeout);
    }


    public static void main(String[] args) {

        boolean endGame = false;


        while (!endGame) {
            DifficultyType dificuldade = null;
            String name = "";
            SexType sexo = null;
            CombatClass combatClass = null;
            Game game;
            Player player;
            Enemy enemy;
            Weapon pickedWeapon = null;
            Armor pickedArmor = null;
            MotivationType motivationType = null;
            int option = 0;
            System.out.println("Seja bem vindo(a) ?? BATALHA FINAL!");

            // Difficulty
            String[] optionsString = new String[DifficultyType.values().length];
            for (DifficultyType dft : DifficultyType.values()) {
                optionsString[dft.ordinal()] = dft.ordinal() + 1 + ". " + dft.name;
            }
            option = optionsLoop(
                    new int[]{1, 3},
                    new String[]{"Escolha do n??vel de dificuldade:"},
                    optionsString
            );
            switch (option) {
                case 1 -> dificuldade = DifficultyType.EASY;
                case 2 -> dificuldade = DifficultyType.NORMAL;
                case 3 -> dificuldade = DifficultyType.HARD;
                default -> System.out.println("Op????o inv??lida");
            }

            game = new Game(dificuldade);
            // NAME
            while (name.isEmpty()) {
                System.out.println("Digite o seu nome");
                name = in.next();
            }

            // SEX
            optionsString = new String[SexType.values().length];
            for (SexType sex : SexType.values()) {
                optionsString[sex.ordinal()] = sex.ordinal() + 1 + ". " + sex.name;
            }
            option = optionsLoop(
                    new int[]{1, 2},
                    new String[]{"Escolha o seu sexo:"},
                    optionsString
            );
            switch (option) {
                case 1 -> sexo = SexType.MALE;
                case 2 -> sexo = SexType.FEMALE;
                default -> System.out.println("Op????o inv??lida");
            }

            // CombatClass
            optionsString = new String[CombatClassType.values().length];
            for (CombatClassType cbt : CombatClassType.values()) {
                optionsString[cbt.ordinal()] = cbt.ordinal() + 1 + ". " + cbt.name;
            }
            option = optionsLoop(
                    new int[]{1, 4},
                    new String[]{"Escolha uma classe:"},
                    optionsString
            );
            switch (option) {
                case 1 -> combatClass = new Barbarian();
                case 2 -> combatClass = new Archer();
                case 3 -> combatClass = new Warrior();
                case 4 -> combatClass = new Mage();
                default -> {
                }
            }

            // Weapon
            assert combatClass != null;
            optionsString = new String[combatClass.weaponsAllowed.size()];
            int i = 0;
            for (WeaponType wp : combatClass.weaponsAllowed) {
                optionsString[i] = i + 1 + ". " + wp.name;
                i++;
            }
            option = optionsLoop(
                    new int[]{1, 3},
                    new String[]{"Escolha uma arma:"},
                    optionsString
            );
            switch (option) {
                case 1 -> pickedWeapon = game.weaponHashMap.get(combatClass.weaponsAllowed.get(0).name);
                case 2 -> pickedWeapon = game.weaponHashMap.get(combatClass.weaponsAllowed.get(1).name);
                case 3 -> pickedWeapon = game.weaponHashMap.get(combatClass.weaponsAllowed.get(2).name);
                default -> System.out.println("Op????o inv??lida");
            }

            // Armor
            optionsString = new String[combatClass.armorsAllowed.size()];
            i = 0;
            for (ArmorType armor : combatClass.armorsAllowed) {
                optionsString[i] = i + 1 + ". " + armor.name;
                i++;
            }
            option = optionsLoop(
                    new int[]{1, 2},
                    new String[]{"Escolha uma armadura:"},
                    optionsString
            );
            switch (option) {
                case 1 -> pickedArmor = game.armorHashMap.get(combatClass.armorsAllowed.get(0).name);
                case 2 -> pickedArmor = game.armorHashMap.get(combatClass.armorsAllowed.get(1).name);
                default -> System.out.println("Op????o inv??lida");
            }


            System.out.println(
                    "A noite se aproxima, a lua j?? surge no c??u, estrelas v??o se acendendo, " +
                            "e sob a luz do crep??sculo voc?? est?? prestes a entrar na fase final da sua miss??o. " +
                            "Voc?? olha para o portal ?? sua frente, e sabe que a partir desse ponto, " +
                            "sua vida mudar?? para sempre.");

            System.out.println(
                    "Mem??rias do caminho percorrido para chegar at?? aqui invadem sua mente. " +
                            "Voc?? se lembra de todos os inimigos j?? derrotados para alcan??ar o covil do l??der maligno. " +
                            "Olha para seu equipamento de combate, j?? danificado e desgastado depois de tantas lutas. " +
                            "Voc?? est?? a um passo de encerrar para sempre esse mal.");

            System.out.println("Buscando uma inje????o de ??nimo, voc?? se for??a a lembrar o que te trouxe at?? aqui.");

            // Motivation
            optionsString = new String[MotivationType.values().length];
            for (MotivationType motiv : MotivationType.values()) {
                optionsString[motiv.ordinal()] = motiv.ordinal() + 1 + ". " + motiv.name;
            }
            option = optionsLoop(
                    new int[]{1, 2},
                    new String[]{"Escolha sua motiva????o para invadir a caverna do inimigo e derrot??-lo:"},
                    optionsString
            );
            switch (option) {
                case 1 -> motivationType = MotivationType.REVENGE;
                case 2 -> motivationType = MotivationType.GLORY;
                default -> System.out.println("Op????o inv??lida");
            }


            assert motivationType != null;
            if (motivationType.equals(MotivationType.REVENGE)) {
                System.out.println(
                        "VINGAN??A: Imagens daquela noite tr??gica invadem sua mente. " +
                                "Voc?? nem precisa se esfor??ar para lembrar, pois essas mem??rias est??o sempre presentes, " +
                                "mesmo que de pano de fundo, quando voc?? tem outros pensamentos em foco, elas nunca o deixaram." +
                                " Elas s??o o combust??vel que te fizeram chegar at?? aqui. E voc?? sabe que n??o ir?? desistir at?? ter " +
                                "vingado a morte daqueles que foram - e pra sempre ser??o - sua fonte de amor e desejo de continuar vivo. O maldito l??der finalmente pagar?? por tanto mal causado na vida de tantos (e principalmente na sua)."

                );
            } else {
                System.out.println(
                        "GL??RIA: Voc?? j?? consegue visualizar na sua mente o povo da cidade te recebendo " +
                                "de bra??os abertos, bardos criando can????es sobre seus feitos her??icos, " +
                                "nobres te presenteando com j??ias e diversas riquezas, taberneiros se " +
                                "recusando a cobrar por suas bebedeiras e comilan??as. Desde j??, voc?? sente " +
                                "o amor do p??blico, te louvando a cada passo que d?? pelas ruas, depois de " +
                                "destruir o vil??o que tanto assombrou a paz de todos. Por??m, voc?? sabe que " +
                                "ainda falta o ??ltimo ato dessa hist??ria. Voc?? se concentra na miss??o. " +
                                "A gl??ria o aguarda, mas n??o antes da ??ltima batalha."

                );
            }

            player = new Player(
                    "Alex",
                    sexo,
                    combatClass,
                    pickedWeapon,
                    pickedArmor,
                    motivationType,
                    0);
            game.setTimeout(1000);

            System.out.println(
                    "Inspirado pelo motivo que te trouxe at?? aqui, voc?? sente seu cora????o ardendo em chamas, " +
                            "suas m??os formigarem em volta da sua arma. Voc?? a segura com firmeza. " +
                            "Seu foco est?? renovado. Voc?? avan??a pelo portal. "
            );

            System.out.println(
                    "A escurid??o te envolve. Uma ilumina????o muito fraca entra pelo portal ??s suas costas. " +
                            "?? sua frente, s?? ?? poss??vel perceber que voc?? se encontra em um corredor extenso. " +
                            "Voc?? s?? pode ir ?? frente, ou desistir."
            );

            int shouldQuit = 0;

            while (shouldQuit != 1 && shouldQuit != 2) {
                System.out.println("1. Desistir");
                System.out.println("2. Seguir");
                shouldQuit = in.nextInt();
            }

            if (shouldQuit == 1) {
                System.out.println(
                        "DESISTIR: o medo invade o seu cora????o e voc?? sente que ainda n??o est?? ?? altura" +
                                " do desafio. Voc?? se volta para a noite l?? fora e corre em dire????o ?? seguran??a. "
                );
                endGame = true;
                continue;
            }

            System.out.println(
                    "SEGUIR: voc?? caminha, atento a todos os seus sentidos, por v??rios metros, " +
                            "at?? visualizar a frente uma fonte de luz, que voc?? imagina ser a chama de uma" +
                            "tocha, vindo de dentro de uma porta aberta."
            );

            System.out.println();
            option = 0;
            while (option < 1 || option > 3) {
                System.out.println(
                        "Voc?? se pergunta se dentro dessa sala pode haver inimigos, " +
                                "ou alguma armadilha, e pondera sobre como passar pela porta."
                );
                System.out.println("1. Andar cuidadosamente");
                System.out.println("2. Correr");
                System.out.println("3. Saltar");
                option = in.nextInt();

                switch (option) {
                    case 1 -> {
                        System.out.println(
                                "ANDANDO: Voc?? toma cuidado e vai caminhando vagarosamente em dire????o ?? luz. " +
                                        "Quando voc?? pisa exatamente embaixo da porta, voc?? sente o ch??o ceder levemente, " +
                                        "como se tivesse pisado em uma pedra solta. Voc?? ouve um ru??do de mecanismos se " +
                                        "movimentando, e uma escotilha se abre no teto atr??s de voc??, no corredor. " +
                                        "Flechas voam da escotilha em sua dire????o, e voc?? salta para dentro da sala, " +
                                        "por??m uma delas te acerta na perna. "
                        );
                        System.out.println("Vida antes da trap -> " + player.getHealth());
                        player.takeTurn(new TrapAction(), player);
                        System.out.println("Vida depois da trap -> " + player.getHealth());
                    }
                    case 2 -> System.out.println(
                            " CORRENDO: Voc?? respira fundo e desata a correr em dire????o ?? sala. Quando passa pela porta, " +
                                    "sente que pisou em uma pedra solta, mas n??o d?? muita import??ncia e segue para dentro da" +
                                    " sala, olhando ao redor ?? procura de inimigos. N??o tem ningu??m, mas voc?? ouve sons de " +
                                    "flechas batendo na pedra atr??s de voc??, e quando se vira, v?? v??rias flechas no ch??o." +
                                    " Espiando pela porta, voc?? entende que pisou em uma armadilha que soltou flechas de uma" +
                                    " escotilha aberta no teto, mas por sorte voc?? entrou correndo e conseguiu escapar desse ataque surpresa. "
                    );
                    case 3 -> System.out.println(
                            "SALTANDO: Voc?? se concentra e pula em dire????o ?? luz, saltando de antes da porta at?? o interior da sala. "
                    );
                    default -> System.out.println("Op????o inv??lida");
                }

            }
            System.out.println();
            System.out.println(
                    "Voc?? se encontra sozinho em uma sala quadrada, contendo uma porta em cada parede. Uma delas foi aquela pela qual voc?? entrou, que estava aberta, e as outras tr??s est??o fechadas. A porta ?? sua frente ?? a maior das quatro, com inscri????es em seu entorno em uma l??ngua que voc?? n??o sabe ler, mas reconhece como sendo a l??ngua antiga utilizada pelo inimigo. Voc?? se aproxima da porta e percebe que ela est?? trancada por duas fechaduras douradas, e voc?? entende que precisar?? primeiro derrotar o que estiver nas outras duas portas laterais, antes de conseguir enfrentar o l??der. "
            );
            System.out.println("Voc?? se dirige para a porta ?? direita.");
            System.out.println();
            System.out.println("PORTA DIREITA: Voc?? se aproxima, tentando ouvir o que acontece porta adentro, mas n??o escuta nada. Segura com mais for??a sua arma com uma m??o, enquanto empurra a porta com a outra. Ao entrar, voc?? se depara com uma sala espa??osa, com v??rios equipamentos de batalha pendurados nas paredes e dispostos em arm??rios e mesas. Voc?? imagina que este seja o arsenal do inimigo, onde est??o guardados os equipamentos que seus soldados utilizam quando saem para espalhar o terror nas cidades e vilas da regi??o. ");

            System.out.println(
                    "Enquanto seu olhar percorre a sala, voc?? ouve a porta se fechando e gira rapidamente para olhar para tr??s. Ali, de p?? entre voc?? e a porta fechada, bloqueando o caminho do seu destino, est?? um dos capit??es do inimigo. Um orque horrendo, de armadura, capacete e espada em punho, em posi????o de combate. Ele avan??a em sua dire????o. "
            );

            enemy = game.enemyHashMap.get("Armeiro");


            System.out.println();
            Battle encounter = new Battle(player, enemy);
            endGame = encounter.reportEncounterResult();
            if (endGame) continue;

            option = 0;
            while (option != 1 && option != 2) {
                System.out.println("Ap??s derrotar o Armeiro, voc?? percebe que seus equipamentos est??o muito danificados, e olha em volta, encarando todas aquelas pe??as de armaduras resistentes e em ??timo estado. ");
                System.out.println("1. Pegar armaduras novas");
                System.out.println("2. N??o quero usar as armaduras do inimigo");
                option = in.nextInt();
                switch (option) {
                    case 1 -> {
                        System.out.println(
                                "Voc?? resolve usar os equipamentos do inimigo contra ele, e trocar algumas pe??as suas, que estavam danificadas, pelas pe??as de armaduras existentes na sala. De armadura nova, voc?? se sente mais protegido para os desafios ?? sua frente."
                        );
                        // save new Armor to hashMap
                        String newArmor = player.getArmor().getType().name + "+2";
                        game.armorHashMap.put(newArmor, new Armor(
                                player.getArmor().getDefencePoints() + 2,
                                player.getArmor().getType()));
                        // equip
                        player.equipArmor(game.armorHashMap.get(newArmor));
                        System.out.println("Voc?? agora tem uma " + player.getArmor().getType() + " com DFP de " + player.getArmor().getDefencePoints());
                    }
                    case 2 -> System.out.println("Voc?? decide que n??o precisa utilizar nada que venha das m??os do inimigo.");
                    default -> System.out.println("Op????o inv??lida");
                }
            }
            System.out.println();

            System.out.println(
                    "Em uma mesa, voc?? encontra uma chave dourada, e sabe que aquela chave abre uma das fechaduras da porta do l??der inimigo. Voc?? pega a chave e guarda numa pequena bolsa que leva presa ao cinto."
            );
            System.out.println();
            System.out.println(
                    "PORTA ESQUERDA: Voc?? retorna ?? sala anterior e se dirige ?? porta da esquerda. Voc?? se aproxima, tentando ouvir o que acontece porta adentro, mas n??o escuta nada. Segura com mais for??a sua arma com uma m??o, enquanto empurra a porta com a outra. Ao entrar, voc?? se depara com uma sala parecida com a do arsenal, mas em vez de armaduras, existem v??rios potes e garrafas de vidro com conte??dos misteriosos e de cores diversas, e voc?? entende que o capit??o que vive ali, realiza experimentos com diversos ingredientes, criando po????es utilizadas pelos soldados para aterrorizar a regi??o." +
                            "No fundo da sala, olhando em sua dire????o, est?? outro dos capit??es do inimigo. Um orque horrendo, de armadura, cajado em punho, em posi????o de combate. Ele avan??a em sua dire????o. ");
            enemy = game.enemyHashMap.get("Alquimista");
            game.setTimeout(1000);
            encounter = new Battle(player, enemy);

            endGame = encounter.reportEncounterResult();
            if (endGame) continue;


            option = 0;
            while (option < 1 || option > 2) {
                System.out.println(
                        "Ap??s derrotar o Alquimista, voc?? olha em volta, tentando reconhecer alguma po????o do estoque do inimigo. Em uma mesa, voc?? reconhece uma pequena garrafa de vidro contendo um l??quido levemente rosado, pega a garrafa e pondera se deve beber um gole. "
                );
                System.out.println("1. Beber");
                System.out.println("2. Deixa pra l??..");
                option = in.nextInt();
                switch (option) {
                    case 1 -> {
                        System.out.println(
                                "Voc?? se sente revigorado para seguir adiante!"
                        );
                        player.setHealthToMax();
                        player.reportHealth();
                    }
                    case 2 -> System.out.println("Voc?? fica receoso de beber algo produzido pelo inimigo");
                    default -> System.out.println("Op????o inv??lida");
                }
            }

            game.setTimeout(1000);
            option = 0;
            while (option != 1) {
                System.out.println(
                        "Ao lado da porta, voc?? v?? uma chave dourada em cima de uma mesa, e sabe que aquela chave abre a outra fechadura da porta do l??der inimigo. Voc?? pega a chave e guarda na pequena bolsa que leva presa ao cinto. "
                );

                System.out.println(
                        "De volta ?? sala das portas, voc?? se dirige ?? porta final. Coloca as chaves nas fechaduras, e a porta se abre. Seu cora????o acelera, mem??rias de toda a sua vida passam pela sua mente, e voc?? percebe que est?? muito pr??ximo do seu objetivo final. Segura sua arma com mais firmeza, foca no combate que voc?? sabe que ir?? se seguir, e adentra a porta. "
                );
                System.out.println(
                        "L?? dentro, voc?? v?? o l??der sentado em uma poltrona dourada, com duas fogueiras de cada lado, e prisioneiros acorrentados ??s paredes. "
                );
                System.out.println(
                        "Ele percebe sua chegada e se levanta com um salto, apanhando seu machado de guerra de l??mina dupla. "
                );
                System.out.println();
                System.out.println("1. Atacar");
                System.out.println("2. Esperar");
                option = in.nextInt();
            }

            enemy = game.enemyHashMap.get("L??der");
            encounter = new Battle(player, enemy);
            game.setTimeout(1000);

            endGame = encounter.reportEncounterResult();
            if (endGame) continue;

            System.out.println("Voc?? conseguiu!");
            if (player.motivation == MotivationType.REVENGE) {
                System.out.println(
                        "VINGAN??A: Voc?? obteve sua vingan??a. Voc?? se ajoelha e cai no choro, invadido por uma sensa????o de al??vio e felicidade. Voc?? se vingou, tudo que sempre quis, est?? feito. Agora voc?? pode seguir sua vida."
                );
            } else {
                System.out.println(
                        "GL??RIA: O ??xtase em que voc?? se encontra n??o cabe dentro de si. Voc?? se ajoelha e grita de alegria. A gl??ria o aguarda, voc?? a conquistou. "
                );
            }


            System.out.println();
            System.out.println("Voc?? se levanta, olha para os prisioneiros, vai de um em um e os liberta, e todos voc??s saem em dire????o ?? noite, retornando ?? cidade. Seu dever est?? cumprido. ");
            System.out.println();
            System.out.println("Fim...");
            endGame = true;
        }


    }
}

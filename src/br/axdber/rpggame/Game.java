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
        enemyHashMap.put("Líder", new Enemy(
                "Líder",
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
                System.out.println("Opção inválida");
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
            System.out.println("Seja bem vindo(a) à BATALHA FINAL!");

            // Difficulty
            String[] optionsString = new String[DifficultyType.values().length];
            for (DifficultyType dft : DifficultyType.values()) {
                optionsString[dft.ordinal()] = dft.ordinal() + 1 + ". " + dft.name;
            }
            option = optionsLoop(
                    new int[]{1, 3},
                    new String[]{"Escolha do nível de dificuldade:"},
                    optionsString
            );
            switch (option) {
                case 1 -> dificuldade = DifficultyType.EASY;
                case 2 -> dificuldade = DifficultyType.NORMAL;
                case 3 -> dificuldade = DifficultyType.HARD;
                default -> System.out.println("Opção inválida");
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
                default -> System.out.println("Opção inválida");
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
                default -> System.out.println("Opção inválida");
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
                default -> System.out.println("Opção inválida");
            }


            System.out.println(
                    "A noite se aproxima, a lua já surge no céu, estrelas vão se acendendo, " +
                            "e sob a luz do crepúsculo você está prestes a entrar na fase final da sua missão. " +
                            "Você olha para o portal à sua frente, e sabe que a partir desse ponto, " +
                            "sua vida mudará para sempre.");

            System.out.println(
                    "Memórias do caminho percorrido para chegar até aqui invadem sua mente. " +
                            "Você se lembra de todos os inimigos já derrotados para alcançar o covil do líder maligno. " +
                            "Olha para seu equipamento de combate, já danificado e desgastado depois de tantas lutas. " +
                            "Você está a um passo de encerrar para sempre esse mal.");

            System.out.println("Buscando uma injeção de ânimo, você se força a lembrar o que te trouxe até aqui.");

            // Motivation
            optionsString = new String[MotivationType.values().length];
            for (MotivationType motiv : MotivationType.values()) {
                optionsString[motiv.ordinal()] = motiv.ordinal() + 1 + ". " + motiv.name;
            }
            option = optionsLoop(
                    new int[]{1, 2},
                    new String[]{"Escolha sua motivação para invadir a caverna do inimigo e derrotá-lo:"},
                    optionsString
            );
            switch (option) {
                case 1 -> motivationType = MotivationType.REVENGE;
                case 2 -> motivationType = MotivationType.GLORY;
                default -> System.out.println("Opção inválida");
            }


            assert motivationType != null;
            if (motivationType.equals(MotivationType.REVENGE)) {
                System.out.println(
                        "VINGANÇA: Imagens daquela noite trágica invadem sua mente. " +
                                "Você nem precisa se esforçar para lembrar, pois essas memórias estão sempre presentes, " +
                                "mesmo que de pano de fundo, quando você tem outros pensamentos em foco, elas nunca o deixaram." +
                                " Elas são o combustível que te fizeram chegar até aqui. E você sabe que não irá desistir até ter " +
                                "vingado a morte daqueles que foram - e pra sempre serão - sua fonte de amor e desejo de continuar vivo. O maldito líder finalmente pagará por tanto mal causado na vida de tantos (e principalmente na sua)."

                );
            } else {
                System.out.println(
                        "GLÓRIA: Você já consegue visualizar na sua mente o povo da cidade te recebendo " +
                                "de braços abertos, bardos criando canções sobre seus feitos heróicos, " +
                                "nobres te presenteando com jóias e diversas riquezas, taberneiros se " +
                                "recusando a cobrar por suas bebedeiras e comilanças. Desde já, você sente " +
                                "o amor do público, te louvando a cada passo que dá pelas ruas, depois de " +
                                "destruir o vilão que tanto assombrou a paz de todos. Porém, você sabe que " +
                                "ainda falta o último ato dessa história. Você se concentra na missão. " +
                                "A glória o aguarda, mas não antes da última batalha."

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
                    "Inspirado pelo motivo que te trouxe até aqui, você sente seu coração ardendo em chamas, " +
                            "suas mãos formigarem em volta da sua arma. Você a segura com firmeza. " +
                            "Seu foco está renovado. Você avança pelo portal. "
            );

            System.out.println(
                    "A escuridão te envolve. Uma iluminação muito fraca entra pelo portal às suas costas. " +
                            "À sua frente, só é possível perceber que você se encontra em um corredor extenso. " +
                            "Você só pode ir à frente, ou desistir."
            );

            int shouldQuit = 0;

            while (shouldQuit != 1 && shouldQuit != 2) {
                System.out.println("1. Desistir");
                System.out.println("2. Seguir");
                shouldQuit = in.nextInt();
            }

            if (shouldQuit == 1) {
                System.out.println(
                        "DESISTIR: o medo invade o seu coração e você sente que ainda não está à altura" +
                                " do desafio. Você se volta para a noite lá fora e corre em direção à segurança. "
                );
                endGame = true;
                continue;
            }

            System.out.println(
                    "SEGUIR: você caminha, atento a todos os seus sentidos, por vários metros, " +
                            "até visualizar a frente uma fonte de luz, que você imagina ser a chama de uma" +
                            "tocha, vindo de dentro de uma porta aberta."
            );

            System.out.println();
            option = 0;
            while (option < 1 || option > 3) {
                System.out.println(
                        "Você se pergunta se dentro dessa sala pode haver inimigos, " +
                                "ou alguma armadilha, e pondera sobre como passar pela porta."
                );
                System.out.println("1. Andar cuidadosamente");
                System.out.println("2. Correr");
                System.out.println("3. Saltar");
                option = in.nextInt();

                switch (option) {
                    case 1 -> {
                        System.out.println(
                                "ANDANDO: Você toma cuidado e vai caminhando vagarosamente em direção à luz. " +
                                        "Quando você pisa exatamente embaixo da porta, você sente o chão ceder levemente, " +
                                        "como se tivesse pisado em uma pedra solta. Você ouve um ruído de mecanismos se " +
                                        "movimentando, e uma escotilha se abre no teto atrás de você, no corredor. " +
                                        "Flechas voam da escotilha em sua direção, e você salta para dentro da sala, " +
                                        "porém uma delas te acerta na perna. "
                        );
                        System.out.println("Vida antes da trap -> " + player.getHealth());
                        player.takeTurn(new TrapAction(), player);
                        System.out.println("Vida depois da trap -> " + player.getHealth());
                    }
                    case 2 -> System.out.println(
                            " CORRENDO: Você respira fundo e desata a correr em direção à sala. Quando passa pela porta, " +
                                    "sente que pisou em uma pedra solta, mas não dá muita importância e segue para dentro da" +
                                    " sala, olhando ao redor à procura de inimigos. Não tem ninguém, mas você ouve sons de " +
                                    "flechas batendo na pedra atrás de você, e quando se vira, vê várias flechas no chão." +
                                    " Espiando pela porta, você entende que pisou em uma armadilha que soltou flechas de uma" +
                                    " escotilha aberta no teto, mas por sorte você entrou correndo e conseguiu escapar desse ataque surpresa. "
                    );
                    case 3 -> System.out.println(
                            "SALTANDO: Você se concentra e pula em direção à luz, saltando de antes da porta até o interior da sala. "
                    );
                    default -> System.out.println("Opção inválida");
                }

            }
            System.out.println();
            System.out.println(
                    "Você se encontra sozinho em uma sala quadrada, contendo uma porta em cada parede. Uma delas foi aquela pela qual você entrou, que estava aberta, e as outras três estão fechadas. A porta à sua frente é a maior das quatro, com inscrições em seu entorno em uma língua que você não sabe ler, mas reconhece como sendo a língua antiga utilizada pelo inimigo. Você se aproxima da porta e percebe que ela está trancada por duas fechaduras douradas, e você entende que precisará primeiro derrotar o que estiver nas outras duas portas laterais, antes de conseguir enfrentar o líder. "
            );
            System.out.println("Você se dirige para a porta à direita.");
            System.out.println();
            System.out.println("PORTA DIREITA: Você se aproxima, tentando ouvir o que acontece porta adentro, mas não escuta nada. Segura com mais força sua arma com uma mão, enquanto empurra a porta com a outra. Ao entrar, você se depara com uma sala espaçosa, com vários equipamentos de batalha pendurados nas paredes e dispostos em armários e mesas. Você imagina que este seja o arsenal do inimigo, onde estão guardados os equipamentos que seus soldados utilizam quando saem para espalhar o terror nas cidades e vilas da região. ");

            System.out.println(
                    "Enquanto seu olhar percorre a sala, você ouve a porta se fechando e gira rapidamente para olhar para trás. Ali, de pé entre você e a porta fechada, bloqueando o caminho do seu destino, está um dos capitães do inimigo. Um orque horrendo, de armadura, capacete e espada em punho, em posição de combate. Ele avança em sua direção. "
            );

            enemy = game.enemyHashMap.get("Armeiro");


            System.out.println();
            Battle encounter = new Battle(player, enemy);
            endGame = encounter.reportEncounterResult();
            if (endGame) continue;

            option = 0;
            while (option != 1 && option != 2) {
                System.out.println("Após derrotar o Armeiro, você percebe que seus equipamentos estão muito danificados, e olha em volta, encarando todas aquelas peças de armaduras resistentes e em ótimo estado. ");
                System.out.println("1. Pegar armaduras novas");
                System.out.println("2. Não quero usar as armaduras do inimigo");
                option = in.nextInt();
                switch (option) {
                    case 1 -> {
                        System.out.println(
                                "Você resolve usar os equipamentos do inimigo contra ele, e trocar algumas peças suas, que estavam danificadas, pelas peças de armaduras existentes na sala. De armadura nova, você se sente mais protegido para os desafios à sua frente."
                        );
                        // save new Armor to hashMap
                        String newArmor = player.getArmor().getType().name + "+2";
                        game.armorHashMap.put(newArmor, new Armor(
                                player.getArmor().getDefencePoints() + 2,
                                player.getArmor().getType()));
                        // equip
                        player.equipArmor(game.armorHashMap.get(newArmor));
                        System.out.println("Você agora tem uma " + player.getArmor().getType() + " com DFP de " + player.getArmor().getDefencePoints());
                    }
                    case 2 -> System.out.println("Você decide que não precisa utilizar nada que venha das mãos do inimigo.");
                    default -> System.out.println("Opção inválida");
                }
            }
            System.out.println();

            System.out.println(
                    "Em uma mesa, você encontra uma chave dourada, e sabe que aquela chave abre uma das fechaduras da porta do líder inimigo. Você pega a chave e guarda numa pequena bolsa que leva presa ao cinto."
            );
            System.out.println();
            System.out.println(
                    "PORTA ESQUERDA: Você retorna à sala anterior e se dirige à porta da esquerda. Você se aproxima, tentando ouvir o que acontece porta adentro, mas não escuta nada. Segura com mais força sua arma com uma mão, enquanto empurra a porta com a outra. Ao entrar, você se depara com uma sala parecida com a do arsenal, mas em vez de armaduras, existem vários potes e garrafas de vidro com conteúdos misteriosos e de cores diversas, e você entende que o capitão que vive ali, realiza experimentos com diversos ingredientes, criando poções utilizadas pelos soldados para aterrorizar a região." +
                            "No fundo da sala, olhando em sua direção, está outro dos capitães do inimigo. Um orque horrendo, de armadura, cajado em punho, em posição de combate. Ele avança em sua direção. ");
            enemy = game.enemyHashMap.get("Alquimista");
            game.setTimeout(1000);
            encounter = new Battle(player, enemy);

            endGame = encounter.reportEncounterResult();
            if (endGame) continue;


            option = 0;
            while (option < 1 || option > 2) {
                System.out.println(
                        "Após derrotar o Alquimista, você olha em volta, tentando reconhecer alguma poção do estoque do inimigo. Em uma mesa, você reconhece uma pequena garrafa de vidro contendo um líquido levemente rosado, pega a garrafa e pondera se deve beber um gole. "
                );
                System.out.println("1. Beber");
                System.out.println("2. Deixa pra lá..");
                option = in.nextInt();
                switch (option) {
                    case 1 -> {
                        System.out.println(
                                "Você se sente revigorado para seguir adiante!"
                        );
                        player.setHealthToMax();
                        player.reportHealth();
                    }
                    case 2 -> System.out.println("Você fica receoso de beber algo produzido pelo inimigo");
                    default -> System.out.println("Opção inválida");
                }
            }

            game.setTimeout(1000);
            option = 0;
            while (option != 1) {
                System.out.println(
                        "Ao lado da porta, você vê uma chave dourada em cima de uma mesa, e sabe que aquela chave abre a outra fechadura da porta do líder inimigo. Você pega a chave e guarda na pequena bolsa que leva presa ao cinto. "
                );

                System.out.println(
                        "De volta à sala das portas, você se dirige à porta final. Coloca as chaves nas fechaduras, e a porta se abre. Seu coração acelera, memórias de toda a sua vida passam pela sua mente, e você percebe que está muito próximo do seu objetivo final. Segura sua arma com mais firmeza, foca no combate que você sabe que irá se seguir, e adentra a porta. "
                );
                System.out.println(
                        "Lá dentro, você vê o líder sentado em uma poltrona dourada, com duas fogueiras de cada lado, e prisioneiros acorrentados às paredes. "
                );
                System.out.println(
                        "Ele percebe sua chegada e se levanta com um salto, apanhando seu machado de guerra de lâmina dupla. "
                );
                System.out.println();
                System.out.println("1. Atacar");
                System.out.println("2. Esperar");
                option = in.nextInt();
            }

            enemy = game.enemyHashMap.get("Líder");
            encounter = new Battle(player, enemy);
            game.setTimeout(1000);

            endGame = encounter.reportEncounterResult();
            if (endGame) continue;

            System.out.println("Você conseguiu!");
            if (player.motivation == MotivationType.REVENGE) {
                System.out.println(
                        "VINGANÇA: Você obteve sua vingança. Você se ajoelha e cai no choro, invadido por uma sensação de alívio e felicidade. Você se vingou, tudo que sempre quis, está feito. Agora você pode seguir sua vida."
                );
            } else {
                System.out.println(
                        "GLÓRIA: O êxtase em que você se encontra não cabe dentro de si. Você se ajoelha e grita de alegria. A glória o aguarda, você a conquistou. "
                );
            }


            System.out.println();
            System.out.println("Você se levanta, olha para os prisioneiros, vai de um em um e os liberta, e todos vocês saem em direção à noite, retornando à cidade. Seu dever está cumprido. ");
            System.out.println();
            System.out.println("Fim...");
            endGame = true;
        }


    }
}

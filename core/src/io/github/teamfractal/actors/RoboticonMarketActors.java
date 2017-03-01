package io.github.teamfractal.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.Roboticon;
import io.github.teamfractal.entity.enums.ResourceType;
import io.github.teamfractal.screens.TimedMenuScreen;

import java.util.ArrayList;
import java.util.HashMap;

public class RoboticonMarketActors extends Table {
	private RoboticonQuest game;
	private TimedMenuScreen screen;
	private Integer roboticonAmount = 0;
	private int currentlySelectedRoboticonPos;
	private Texture roboticonTexture;
	private Label topText;
	private Label playerStats;
	private Label marketStats;
	private Label roboticonID;
	private Image roboticonImage = new Image();
	private Image background;
	private float backgroundX, backgroundY;

	private static final Texture TEXTURE_UNCUSTOMISED;
	private static final Texture TEXTURE_ENERGY;
	private static final Texture TEXTURE_ORE;
	private static final Texture TEXTURE_FOOD;
	private static final Texture TEXTURE_NO_ROBOTICONS;

	private ArrayList<Roboticon> roboticons = new ArrayList<Roboticon>();

	static {
		TEXTURE_UNCUSTOMISED = new Texture(Gdx.files.internal("roboticon_images/robot.png"));
		TEXTURE_ENERGY = new Texture(Gdx.files.internal("roboticon_images/robot_energy.png"));
		TEXTURE_ORE = new Texture(Gdx.files.internal("roboticon_images/robot_ore.png"));
		TEXTURE_FOOD = new Texture(Gdx.files.internal("roboticon_images/robot_food.png"));
		TEXTURE_NO_ROBOTICONS = new Texture(Gdx.files.internal("roboticon_images/no_roboticons.png"));
	}

	public RoboticonMarketActors(final RoboticonQuest game, TimedMenuScreen screen) {
		this.game = game;
		this.screen = screen;

		this.roboticonID = new Label("", game.skin);
		this.marketStats = new Label("", game.skin);

		background = new Image(new Texture(Gdx.files.internal("background/robotfactory.jpg")));

		widgetUpdate();

		// Buy Roboticon Text: Top Left
		final Label lblBuyRoboticon = new Label("Purchase Roboticons", game.skin);

		//Roboticon text to go next to + and - buttons
		final Label lblRoboticons = new Label("Quantity:", game.skin);

		final Label lblRoboticonAmount = new Label(roboticonAmount.toString(), game.skin);

		// Button to increase number of roboticons bought
		final TextButton addRoboticonButton = new TextButton("+", game.skin);
		addRoboticonButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				roboticonAmount += 1;
				lblRoboticonAmount.setText(roboticonAmount.toString());
			}
		});

		// Button to decrease number of roboticons bought
		final TextButton subRoboticonButton = new TextButton("-", game.skin);
		subRoboticonButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (roboticonAmount > 0) {
					roboticonAmount -= 1;
					lblRoboticonAmount.setText(roboticonAmount.toString());
				}
			}
		});

		// Button to buy the selected amount of roboticons from the market
		final TextButton buyRoboticonsButton = new TextButton("BUY: "+Integer.toString(game.market.getSellPrice(ResourceType.ROBOTICON)), game.skin);
		buyRoboticonsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.getPlayer().purchaseRoboticonsFromMarket(roboticonAmount, game.market);
				roboticonAmount = 0;
				lblRoboticonAmount.setText(roboticonAmount.toString());
				widgetUpdate();
			}
		});
		
		final Label marketStatistics = new Label("Market Statistics:", game.skin);
		

		// Current Roboticon Text: Top Right
		String playerRoboticonText = "Player " + (game.getPlayerInt() + 1) + "'s Roboticons:";
		final Label lblCurrentRoboticon = new Label(playerRoboticonText, game.skin);

		// Image widget which displays the roboticon in the player's inventory

		// Buttons to move backwards and forwards in the player's roboticon inventory
		final TextButton moveLeftRoboticonInventoryBtn = new TextButton("<", game.skin);
		moveLeftRoboticonInventoryBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (currentlySelectedRoboticonPos > 0) {
					currentlySelectedRoboticonPos--;
					setCurrentlySelectedRoboticon(currentlySelectedRoboticonPos);
				}
			}
		});

		final TextButton moveRightRoboticonInventoryBtn = new TextButton(">", game.skin);
		moveRightRoboticonInventoryBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (currentlySelectedRoboticonPos < roboticons.size() - 1) {
					currentlySelectedRoboticonPos++;
					setCurrentlySelectedRoboticon(currentlySelectedRoboticonPos);
				}
			}
		});


		// Purchase Customisation Text: Bottom Right
		final Label lblPurchaseCustomisation = new Label("Customisation Type:", game.skin);

		// Drop down menu to select how to customise the selected roboticion
		final SelectBox<String> customisationDropDown = new SelectBox<String>(game.skin);
		String[] customisations = {"Energy", "Ore", "Food"};
		customisationDropDown.setItems(customisations);

		// Button to buy the selected customisation and customise the selected roboticon
		final TextButton buyCustomisationButton = new TextButton("CUSTOMIZE: "+Integer.toString(game.market.getSellPrice(ResourceType.CUSTOMISATION)), game.skin);
		buyCustomisationButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (-1 == currentlySelectedRoboticonPos) {
					// nothing selected.
					return;
				}
				HashMap<String, ResourceType> converter = new HashMap<String, ResourceType>();
				converter.put("Energy", ResourceType.ENERGY);
				converter.put("Ore", ResourceType.ORE);
				converter.put("Food", ResourceType.FOOD);
				Roboticon roboticonToCustomise = roboticons.get(currentlySelectedRoboticonPos);

				game.getPlayer().purchaseCustomisationFromMarket(converter.get(customisationDropDown.getSelected()), roboticonToCustomise, game.market);
				widgetUpdate();
			}
		});

		final TextButton nextButton = new TextButton("Next ->", game.skin);
		nextButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.nextPhase();
			}
		});

		addActor(background);

		// Top Row Text
		add(lblBuyRoboticon).padTop(40).padLeft(80);
		add();
		add();
		add(lblCurrentRoboticon).padTop(40).padLeft(100);

		row();

		// Roboticon inc & dec buttons,
		add(lblRoboticons).padTop(20).padLeft(-132);
		add(subRoboticonButton).padTop(20).padLeft(-232);
		add(lblRoboticonAmount).padTop(20).padLeft(-142);
		add(addRoboticonButton).padTop(20).padLeft(-342);

		add();
		add();
		add();

		row();

		// Roboticon in inventory selection (moved to different row to preserve position of other buttons)
		add();
		add(buyRoboticonsButton).padLeft(-200).padBottom(230);
		add();
		add();

		add(moveLeftRoboticonInventoryBtn).padTop(40).padLeft(-400).padBottom(200);
		add(roboticonImage).padLeft(-180).padRight(125).padBottom(180).padTop(-50);
		add(moveRightRoboticonInventoryBtn).padTop(40).padLeft(-200).padBottom(200);

		row();

		add();
		add(marketStatistics).padLeft(-300).padTop(-270);
		add();
		add();

		add();
		add(roboticonID).padLeft(-305).padTop(-270);
		
		row();
		// Purchase customisation label
		add();
		add(marketStats).padLeft(-300).padTop(-200);
		add();
		add();

		add();
		add(lblPurchaseCustomisation).padLeft(-300).padTop(-220);

		row();

		// Customisation Drop Down Menu
		add();
		add();
		add();
		add();

		add();
		add(customisationDropDown).padLeft(-300).padTop(-160);

		row();

		// Buy Customisation Button
		add();
		add();
		add();
		add();

		add();
		add(buyCustomisationButton).padLeft(-300).padTop(-80);

		row();

		add();
		add();
		add();
		add();

		add();
		add(nextButton).padLeft(0).padTop(-20);

	}

	public String padZero(int number, int length) {
		String s = "" + number;
		while (s.length() < length) {
			s = "0" + s;
		}
		return s;
	}

	public void setCurrentlySelectedRoboticon(int roboticonPos) {
		if (roboticonPos != -1) {

			ResourceType roboticonType = roboticons.get(roboticonPos).getCustomisation();

			switch (roboticonType) {
				case Unknown:
					roboticonTexture = TEXTURE_UNCUSTOMISED;
					break;
				case ENERGY:
					roboticonTexture = TEXTURE_ENERGY;
					break;
				case ORE:
					roboticonTexture = TEXTURE_ORE;
					break;
				case FOOD:
					roboticonTexture = TEXTURE_FOOD;
					break;
				default:
					break;
			}

			int id = roboticons.get(roboticonPos).getID();
			this.roboticonID.setText("Roboticon Issue Number: " + padZero(id, 4));

		} else {
			roboticonTexture = TEXTURE_NO_ROBOTICONS;
			this.roboticonID.setText("Roboticon Issue Number: ####");
		}

		roboticonImage.setDrawable(new TextureRegionDrawable(new TextureRegion(roboticonTexture)));
	}

	public void widgetUpdate() {
		roboticons.clear();
		for (Roboticon r : game.getPlayer().getRoboticons()) {
			if (!r.isInstalled()) {
				roboticons.add(r);
			}
		}

		// Draws turn and phase info on screen
		if (this.topText != null) this.topText.remove();
		String phaseText = "Player " + (game.getPlayerInt() + 1) + "; Phase " + game.getPhase();
		this.topText = new Label(phaseText, game.skin);
		topText.setWidth(120);
		topText.setPosition(screen.getStage().getWidth() / 2 - 40, screen.getStage().getViewport().getWorldHeight() - 20);
		screen.getStage().addActor(topText);

		// Draws player stats on screen
		if (this.playerStats != null) this.playerStats.remove();
		String statText = "Ore: " + game.getPlayer().getOre() + " Energy: " + game.getPlayer().getEnergy() + " Food: "
				+ game.getPlayer().getFood() + " Money: " + game.getPlayer().getMoney();
		this.playerStats = new Label(statText, game.skin);
		playerStats.setWidth(250);
		playerStats.setPosition(0, screen.getStage().getViewport().getWorldHeight() - 20);
		screen.getStage().addActor(playerStats);

		if (roboticons.size() == 0) {
			currentlySelectedRoboticonPos = -1;
		} else if (currentlySelectedRoboticonPos == -1) {
			currentlySelectedRoboticonPos = 0;
		}

		setCurrentlySelectedRoboticon(currentlySelectedRoboticonPos);
		
		marketStats.setText("Available: " + game.market.getResource(ResourceType.ROBOTICON));

	}

	public void resizeScreen(float width, float height) {
		backgroundX = width/background.getWidth();
		backgroundY = height/background.getHeight();
		background.setScale(backgroundX, backgroundY);
	}

}

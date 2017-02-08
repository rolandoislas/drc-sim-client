package com.rolandoislas.drcsimclient.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.rolandoislas.drcsimclient.Client;

import java.util.ArrayList;

/**
 * Created by Rolando on 2/7/2017.
 */
public class StageList extends Stage {
	private final List<String> list;
	private final Label title;
	private final SelectBox<String> dropdown;
	private ArrayList<ChangeListener> listeners = new ArrayList<ChangeListener>();

	public StageList(boolean enableDropdown) {
		// List
		Skin listSkin = new Skin();
		listSkin.add("selection", new Texture("image/textfield-selection.png"));
		List.ListStyle listStyle = new List.ListStyle();
		listStyle.font = new BitmapFont(Gdx.files.internal("font/collvetica.fnt"));
		listStyle.fontColorUnselected = new Color(1, 1, 1, 1);
		listStyle.fontColorSelected = new Color(.5f, .5f, .5f, 1);
		listStyle.selection = listSkin.getDrawable("selection");
		list = new List<String>(listStyle);
		list.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		list.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				listItemSelected(event, actor);
			}
		});
		// Scroll Pane
		ScrollPane scrollPane = new ScrollPane(list);
		scrollPane.setBounds(0, enableDropdown ? Gdx.graphics.getHeight() * .1f : 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight() * (enableDropdown ? .8f : .9f));
		addActor(scrollPane);
		// Title
		Label.LabelStyle labelStyle = new Label.LabelStyle();
		labelStyle.font = listStyle.font;
		labelStyle.fontColor = listStyle.fontColorSelected;
		labelStyle.background = listStyle.selection;
		title = new Label("", labelStyle);
		title.setBounds(0, Gdx.graphics.getHeight() * .9f, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight() * .1f);
		title.setAlignment(Align.center);
		addActor(title);
		// Controller Dropdown
		Skin selectBoxSkin = new Skin();
		selectBoxSkin.add("background", new Texture("image/textfield-selection.png"));
		SelectBox.SelectBoxStyle selectBoxStyle = new SelectBox.SelectBoxStyle();
		selectBoxStyle.font = new BitmapFont(Gdx.files.internal("font/collvetica.fnt"));
		selectBoxStyle.fontColor = new Color(0, 0, 0, 1);
		selectBoxStyle.disabledFontColor = selectBoxStyle.fontColor;
		selectBoxStyle.background = selectBoxSkin.getDrawable("background");
		selectBoxStyle.backgroundDisabled = selectBoxStyle.background;
		selectBoxStyle.backgroundOpen = selectBoxStyle.background;
		selectBoxStyle.backgroundOver = selectBoxStyle.background;
		selectBoxStyle.listStyle = new List.ListStyle();
		selectBoxStyle.listStyle.font = selectBoxStyle.font;
		selectBoxStyle.listStyle.fontColorSelected = selectBoxStyle.fontColor;
		selectBoxStyle.listStyle.fontColorUnselected = selectBoxStyle.fontColor;
		selectBoxStyle.listStyle.background = selectBoxStyle.background;
		selectBoxStyle.listStyle.selection = selectBoxStyle.background;
		selectBoxStyle.scrollStyle = new ScrollPane.ScrollPaneStyle();
		selectBoxStyle.scrollStyle.background = selectBoxStyle.background;
		dropdown = new SelectBox<String>(selectBoxStyle);
		dropdown.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() * .1f);
		for (Controller controller : Controllers.getControllers()) {
			dropdown.getItems().add(controller.getName());
			dropdown.setItems(dropdown.getItems().toArray());
		}
		if (enableDropdown)
			addActor(dropdown);
	}

	public StageList() {
		this(false);
	}

	private void listItemSelected(ChangeListener.ChangeEvent event, Actor actor) {
		if (list.getSelectedIndex() > -1)
			listeners.get(list.getSelectedIndex()).changed(event, actor);
	}

	void addItem(String name, ChangeListener changeListener) {
		list.getItems().add(name);
		listeners.add(changeListener);
	}

	void addStageChangeItem(String itemName, final Class stage) {
		addItem(itemName, new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				try {
					Client.setStage((com.badlogic.gdx.scenes.scene2d.Stage) stage.newInstance());
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		});
	}

	void setTitle(String title) {
		this.title.setText(title);
	}

	List<String> getList() {
		return list;
	}

	SelectBox<String> getDropdown() {
		return dropdown;
	}
}

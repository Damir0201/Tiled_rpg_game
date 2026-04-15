# JavaFX RPG Project
A 2D Role-Playing Game engine built with JavaFX, featuring procedural generation and a dedicated systems-driven architecture.

## Architecture Overview
The project follows a **Modified MVC** pattern with a centralized **Orchestrator** to manage game states and interactions.

```mermaid
%%{init: {'theme': 'base', 'themeVariables': { 'primaryColor': '#5D5CDE', 'edgeLabelBackground':'#ffffff', 'tertiaryColor': '#fff'}}}%%
graph TD
    %% --- STYLE DEFINITIONS ---
    classDef ui_layer fill:#f9f,stroke:#333,stroke-width:2px,rx:10,ry:10,color:#333;
    classDef logic_layer fill:#ccf,stroke:#333,stroke-width:2px,rx:10,ry:10,color:#333;
    classDef data_layer fill:#ff9,stroke:#333,stroke-width:2px,rx:10,ry:10,color:#333;
    classDef singleton fill:#9f9,stroke:#333,stroke-width:2px,stroke-dasharray: 5 5,rx:15,ry:15,color:#333;
    classDef orchestrator fill:#ff9999,stroke:#e60000,stroke-width:3px,rx:12,ry:12,color:#333;

    %% --- PRESENTATION LAYER ---
    subgraph UI_Layer [Presentation Layer]
        direction TB
        A[Launcher] --> B(MenuController)
        B --> C(CharacterSelect)
        C --> D{GameEngine}
        D --> E(UIManager / HUD)
        F(Shop / Inventory) <--> D
    end

    %% --- LOGIC LAYER ---
    subgraph Logic_Layer [Systems Layer]
        direction LR
        D --- G[MovementSystem]
        D --- H[CombatSystem]
        D --- I[InteractionSystem]
        D --- J[AcademySystem]
        K((GameManager)) -.->|Global Access| D
        K -.->|Player State| F
    end

    %% --- DATA LAYER ---
    subgraph Data_Layer [Domain Layer]
        direction BT
        L[Map Object] --> M[Tiles / TypeTile]
        L --> N[Enemy Entities]
        D --> O[Hero Classes]
        P[Generators] --- L
    end

    %% --- APPLY STYLES ---
    class UI_Layer ui_layer;
    class Logic_Layer logic_layer;
    class Data_Layer data_layer;
    class D orchestrator;
    class K singleton;
```
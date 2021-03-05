<div style="text-align:center display: block; margin-left: auto; margin-right: auto;"><img src="https://user-images.githubusercontent.com/45496261/110120178-fbfe4200-7dbc-11eb-9793-5dbf69610db2.png" /></div>


- Create Customized ItemStacks with Meta in one line of code
- Working from 1.7.x to 1.16.x
- Highly customizable (ItemMeta, LeatherMeta, PotionMeta...)
- Easy to use

## Manual

Copy ```MeowItemCreator.java``` and ```Reflection.java``` in your project

## Usage

Now you can create easily ItemStacks by instancing a new ItemStack object and adding the mode ```done()```.

```python
ItemStack itemStack = new MeowItemCreator(Material.STONE).done();
```

## Customization
Just by looking the code of ```MeowItemCreator.java``` you can see all the methods available to customize your ItemStacks like:

- Data 💾
- Display Name 📋
- Lore 🕯️
- Amount 🤏🏽
- Durability / Breakability 📊
- Enchantments ✨
- ItemFlags ⛳
- PotionEffects ⚗️
- Color (RGB) 🟥🟩🟦
- Owner (Skulls) 🐲
- Head Texture (Skulls) 📷

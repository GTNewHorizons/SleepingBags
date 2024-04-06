package ru.npo6ka.sleepingbag;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class BlockRenderer implements ISimpleBlockRenderingHandler {

    private final int renderId;
    public boolean flipTexture;
    public boolean renderAllFaces;
    RenderPlayer player;

    public BlockRenderer() {
        this.renderId = RenderingRegistry.getNextAvailableRenderId();
    }

    public void renderInventoryBlock(final Block block, final int metadata, final int modelId,
            final RenderBlocks renderer) {}

    public double[] rotateLeft() {
        return new double[8];
    }

    public boolean renderWorldBlock(final IBlockAccess world, final int x, final int y, final int z, final Block block,
            final int modelId, final RenderBlocks renderer) {
        final Tessellator tessellator = Tessellator.instance;
        final Block bed = world.getBlock(x, y, z);
        final int dir = bed.getBedDirection(world, x, y, z);
        final boolean flag = bed.isBedFoot(world, x, y, z);
        final float f = 0.5f;
        final float f2 = 1.0f;
        final float f3 = 0.8f;
        final float f4 = 0.6f;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        tessellator.setColorOpaque_F(f, f, f);
        TextureSquare square = new TextureSquare(this.getBlockIcon(block, world, x, y, z, 0));
        if (dir == 0) {
            square.rotateRight();
        } else if (dir == 2) {
            square.rotateLeft();
        } else if (dir == 3) {
            square.rotateDouble();
        }
        double d4 = x + 0.0;
        double d5 = x + 1.0;
        double d6 = y + 0.0;
        double d7 = z + 0.0;
        double d8 = z + 1.0;
        tessellator.addVertexWithUV(d4, d6, d8, square.node0.x, square.node0.y);
        tessellator.addVertexWithUV(d4, d6, d7, square.node1.x, square.node1.y);
        tessellator.addVertexWithUV(d5, d6, d7, square.node2.x, square.node2.y);
        tessellator.addVertexWithUV(d5, d6, d8, square.node3.x, square.node3.y);
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y + 1, z));
        tessellator.setColorOpaque_F(f2, f2, f2);
        square = new TextureSquare(this.getBlockIcon(block, world, x, y, z, 1));
        if (dir == 0) {
            square.rotateRight();
        } else if (dir == 1) {
            square.rotateDouble();
        } else if (dir == 2) {
            square.rotateLeft();
        }
        d6 = y + 0.25;
        tessellator.addVertexWithUV(d5, d6, d8, square.node0.x, square.node0.y);
        tessellator.addVertexWithUV(d5, d6, d7, square.node1.x, square.node1.y);
        tessellator.addVertexWithUV(d4, d6, d7, square.node2.x, square.node2.y);
        tessellator.addVertexWithUV(d4, d6, d8, square.node3.x, square.node3.y);
        int k1 = Direction.directionToFacing[dir];
        if (flag) {
            k1 = Direction.directionToFacing[Direction.rotateOpposite[dir]];
        }
        if (k1 != 2 && block.shouldSideBeRendered(world, x, y, z - 1, 2)) {
            tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y + 1, z));
            tessellator.setColorOpaque_F(f3, f3, f3);
            square = new TextureSquare(this.getBlockIcon(block, world, x, y, z, 2));
            if (dir == 0) {
                square.invertHorizontal();
            } else if (dir == 1) {
                square.rotateDouble();
            } else if (dir == 2) {
                square.rotateDouble();
            } else if (dir == 3) {
                square.invertHorizontal();
            }
            d4 = x + 0.0;
            d5 = x + 1.0;
            d6 = y + 0.0;
            d7 = y + 1.0;
            d8 = z + 0.0;
            tessellator.addVertexWithUV(d5, d7, d8, square.node0.x, square.node0.y);
            tessellator.addVertexWithUV(d5, d6, d8, square.node1.x, square.node1.y);
            tessellator.addVertexWithUV(d4, d6, d8, square.node2.x, square.node2.y);
            tessellator.addVertexWithUV(d4, d7, d8, square.node3.x, square.node3.y);
        }
        if (k1 != 3 && block.shouldSideBeRendered(world, x, y, z + 1, 3)) {
            tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y + 1, z));
            tessellator.setColorOpaque_F(f3, f3, f3);
            square = new TextureSquare(this.getBlockIcon(block, world, x, y, z, 3));
            if (dir == 0) {
                square.invertHorizontal();
            } else if (dir == 1) {
                square.rotateDouble();
            } else if (dir == 2) {
                square.rotateDouble();
            } else if (dir == 3) {
                square.invertHorizontal();
            }
            d4 = x + 0.0;
            d5 = x + 1.0;
            d6 = y + 0.0;
            d7 = y + 1.0;
            d8 = z + 1.0;
            tessellator.addVertexWithUV(d4, d7, d8, square.node3.x, square.node3.y);
            tessellator.addVertexWithUV(d4, d6, d8, square.node2.x, square.node2.y);
            tessellator.addVertexWithUV(d5, d6, d8, square.node1.x, square.node1.y);
            tessellator.addVertexWithUV(d5, d7, d8, square.node0.x, square.node0.y);
        }
        if (k1 != 4 && block.shouldSideBeRendered(world, x - 1, y, z, 4)) {
            tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y + 1, z));
            tessellator.setColorOpaque_F(f4, f4, f4);
            square = new TextureSquare(this.getBlockIcon(block, world, x, y, z, 4));
            if (dir == 0) {
                square.invertHorizontal();
            } else if (dir == 1) {
                square.rotateDouble();
            } else if (dir == 2) {
                square.rotateDouble();
            } else if (dir == 3) {
                square.invertHorizontal();
            }
            d4 = x + 0.0;
            d5 = y + 0.0;
            d6 = y + 1.0;
            d7 = z + 0.0;
            d8 = z + 1.0;
            tessellator.addVertexWithUV(d4, d6, d7, square.node3.x, square.node3.y);
            tessellator.addVertexWithUV(d4, d5, d7, square.node2.x, square.node2.y);
            tessellator.addVertexWithUV(d4, d5, d8, square.node1.x, square.node1.y);
            tessellator.addVertexWithUV(d4, d6, d8, square.node0.x, square.node0.y);
        }
        if (k1 != 5 && block.shouldSideBeRendered(world, x + 1, y, z, 5)) {
            tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y + 1, z));
            tessellator.setColorOpaque_F(f4, f4, f4);
            square = new TextureSquare(this.getBlockIcon(block, world, x, y, z, 5));
            if (Direction.directions[dir].equals("SOUTH")) {
                square.rotateDouble();
            } else if (dir == 1) {
                square.invertHorizontal();
            } else if (dir == 2) {
                square.invertHorizontal();
            } else if (dir == 3) {
                square.invertHorizontal();
            }
            d4 = x + 1.0;
            d5 = y + 0.0;
            d6 = y + 1.0;
            d7 = z + 0.0;
            d8 = z + 1.0;
            tessellator.addVertexWithUV(d4, d6, d8, square.node3.x, square.node3.y);
            tessellator.addVertexWithUV(d4, d5, d8, square.node2.x, square.node2.y);
            tessellator.addVertexWithUV(d4, d5, d7, square.node1.x, square.node1.y);
            tessellator.addVertexWithUV(d4, d6, d7, square.node0.x, square.node0.y);
        }
        return true;
    }

    public IIcon getBlockIcon(final Block block, final IBlockAccess p_147793_2_, final int p_147793_3_,
            final int p_147793_4_, final int p_147793_5_, final int p_147793_6_) {
        return this.getIconSafe(block.getIcon(p_147793_2_, p_147793_3_, p_147793_4_, p_147793_5_, p_147793_6_));
    }

    public IIcon getIconSafe(IIcon icon) {
        if (icon == null) {
            icon = ((TextureMap) Minecraft.getMinecraft().getTextureManager()
                    .getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite("missingno");
        }
        return icon;
    }

    public boolean shouldRender3DInInventory(final int modelId) {
        return false;
    }

    public int getRenderId() {
        return this.renderId;
    }

    public final class TextureSquare {

        public Position node0;
        public Position node1;
        public Position node2;
        public Position node3;

        TextureSquare(final double node0X, final double node0Y, final double node1X, final double node1Y,
                final double node2X, final double node2Y, final double node3X, final double node3Y) {
            this.node0 = new Position(node0X, node0Y);
            this.node1 = new Position(node1X, node1Y);
            this.node2 = new Position(node2X, node2Y);
            this.node3 = new Position(node3X, node3Y);
        }

        TextureSquare(final IIcon iicon) {
            final double MinU = iicon.getMinU();
            final double MaxU = iicon.getMaxU();
            final double MinV = iicon.getMinV();
            final double MaxV = iicon.getMaxV();
            this.node0 = new Position(MaxU, MaxV);
            this.node1 = new Position(MaxU, MinV);
            this.node2 = new Position(MinU, MinV);
            this.node3 = new Position(MinU, MaxV);
        }

        public void rotateRight() {
            final Position node = this.node0;
            this.node0 = this.node1;
            this.node1 = this.node2;
            this.node2 = this.node3;
            this.node3 = node;
        }

        public void rotateLeft() {
            final Position node = this.node0;
            this.node0 = this.node3;
            this.node3 = this.node2;
            this.node2 = this.node1;
            this.node1 = node;
        }

        public void rotateDouble() {
            Position node = this.node0;
            this.node0 = this.node2;
            this.node2 = node;
            node = this.node3;
            this.node3 = this.node1;
            this.node1 = node;
        }

        public void invertVertical() {
            Position node = this.node0;
            this.node0 = this.node3;
            this.node3 = node;
            node = this.node2;
            this.node2 = this.node1;
            this.node1 = node;
        }

        public void invertHorizontal() {
            Position node = this.node0;
            this.node0 = this.node1;
            this.node1 = node;
            node = this.node2;
            this.node2 = this.node3;
            this.node3 = node;
        }

        public final class Position {

            public double x;
            public double y;

            public Position(final double x, final double y) {
                this.x = x;
                this.y = y;
            }
        }
    }
}

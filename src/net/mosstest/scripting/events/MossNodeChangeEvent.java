package net.mosstest.scripting.events;

import net.mosstest.scripting.MapNode;
import net.mosstest.scripting.MossItem;
import net.mosstest.scripting.NodePosition;
import net.mosstest.scripting.Player;

// TODO: Auto-generated Javadoc
/**
 * The Class MossNodeChangeEvent.
 */
public class MossNodeChangeEvent implements IMossEvent{

	/**
	 * The Enum NodeActionType.
	 */
	public enum NodeActionType {
		/**
		 * Fired when the user fully digs a node.
		 */
		NODE_ACTION_DIG,
		
		/** Fired when the user punches (left-clicks) a node. */
		NODE_ACTION_PUNCH,
		/**
		 * Fired when the user punches a node with a right-click. The default
		 * action is to place a node; onDig should return false to avoid this.
		 */
		NODE_ACTION_RIGHTPUNCH,
		/**
		 * Fired when the user punches a node with a middle-click.
		 */
		NODE_ACTION_MIDDLE_MOUSE_PUNCH,
		/**
		 * Fired when the user right-clicks the node while pressing shift. This
		 * is generally a sign of attempting to place a node as opposed to
		 * right-punching it.
		 */
		NODE_ACTION_PLACE,
		/**
		 * Currently unused.
		 */
		NODE_ACTION_REPLACE
	}

	/**
	 * The player performing the action, or null for none (e.g. a plugin raising
	 * a dig event, for example via a node that is designed to dig another node
	 * with a ceratin tool and is raising the event to be evaluated as if it
	 * occurs with that tool.
	 */
	public final Player pl;
	/**
	 * The NodePosition of the node in question.
	 */
	public final NodePosition pos;
	/**
	 * A sequence number for this event. Refer to {@link net.mosstest.scripting.events}.
	 */
	public final long seqnum;
	
	/** The type. */
	public final NodeActionType type;
	
	/** The node before. */
	public final MapNode nodeBefore;
	
	/** The wield item. */
	public final MossItem wieldItem;

	/**
	 * Instantiates a new moss node change event.
	 *
	 * @param pl the pl
	 * @param pos the pos
	 * @param seqnum the seqnum
	 * @param type the type
	 * @param nodeBefore the node before
	 * @param wieldItem the wield item
	 */
	public MossNodeChangeEvent(Player pl, NodePosition pos, long seqnum,
			NodeActionType type, MapNode nodeBefore, MossItem wieldItem) {
		this.pl = pl;
		this.pos = pos;
		this.seqnum = seqnum;
		this.type = type;
		this.nodeBefore = nodeBefore;
		this.wieldItem = wieldItem;
	}

}

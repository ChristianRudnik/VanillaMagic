package com.github.sejoslaw.vanillamagic.common.item.book;

import com.github.sejoslaw.vanillamagic.api.item.ICustomItem;

/**
 * Books should be added to BookRegistry before the PostInitialization.<br>
 * In PostInitialization books recipes will be registered.
 * 
 * @author Sejoslaw - https://github.com/Sejoslaw
 */
public interface IBook extends ICustomItem {
	/**
	 * @return Returns the index of the book (for easier searching).
	 */
	int getBookID();

	/**
	 * @return Returns the unique NBT Tag for this Book.
	 */
	default public String getUniqueNBTName() {
		return BookRegistry.BOOK_NBT_UID;
	}
}
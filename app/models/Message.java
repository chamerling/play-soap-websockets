/*
 * Copyright 2011 Christophe Hamerling
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Lob;

import play.Play;
import play.db.jpa.Model;

/**
 * @author chamerling
 * 
 */
@Entity
public class Message extends Model {

	public static long MAX = 10;

	public long date;

	@Lob
	public String payload;

	public synchronized static void push(Message message) {
		if (message == null) {
			return;
		}
		long count = count();
		if (count >= getMaxSize()) {
			// remove oldest message
			List<Message> list = Message.find("order by date asc").fetch(1);
			for (Message message2 : list) {
				message2.delete();
			}
		}
		message.save();
	}
	
	public static long getMaxSize() {
		long result = MAX;
		Object o = Play.configuration.get("app.message.size");
		if (o != null) {
			try {
				result = Long.parseLong(o.toString());
			} catch (NumberFormatException e) {
			}
		}
		return result;
	}

}

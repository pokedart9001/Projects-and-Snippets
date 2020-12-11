import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.ModifyMessageRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class QuickMark
{
	static void quickMark(Gmail service, List<String> labelsToList, List<String> labelsToKeep, List<String> labelsToRemove) throws IOException
	{
		List<Message> messages = listMessagesWithLabels(service, labelsToList);
		List<Message> messagesToKeep = listMessagesWithLabels(service, labelsToKeep);
		
		for (Message message : messages)
		{
			if (!messagesToKeep.contains(message))
			{
				modifyMessage(service, message.getId(), labelsToRemove);
			}
		}
	}
	
	private static List<Message> listMessagesWithLabels(Gmail service, List<String> labelIds) throws IOException
	{
		ListMessagesResponse response = service.users().messages().list("me").setLabelIds(labelIds).execute();
		
		List<Message> messages = new ArrayList<>();
		while (response.getMessages() != null)
		{
			messages.addAll(response.getMessages());
			if (response.getNextPageToken() != null)
			{
				String pageToken = response.getNextPageToken();
				response = service.users().messages().list("me").setLabelIds(labelIds).setPageToken(pageToken).execute();
			}
			else
			{
				break;
			}
		}
		
		return messages;
	}
	
	private static void modifyMessage(Gmail service, String messageId, List<String> labelsToRemove) throws IOException
	{
		ModifyMessageRequest mods = new ModifyMessageRequest().setRemoveLabelIds(labelsToRemove);
		service.users().messages().modify("me", messageId, mods).execute();
	}
}
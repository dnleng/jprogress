set (0, 'defaultaxesfontname', 'Helvetica')
set (0, 'defaultaxesfontsize', 14)
set (0, 'defaulttextfontname', 'Helvetica')
set (0, 'defaulttextfontsize', 14) 

%% Load data
maxLength = 0;
for i = 5:190
    disp(int2str(i));
    perfMatrix = csvread(strcat('exp7/kr-online-1-',int2str(i),'.csv'),1,0);
    verdictTrue = perfMatrix(:,3);
    verdictFalse = perfMatrix(:,4);
    verdictUnknown = perfMatrix(:,5);
    verdictNone = perfMatrix(:,6);
    dataFalse{i} = verdictFalse;
    maxLength = max(maxLength, size(verdictFalse));
    dataUnknown{i} = verdictUnknown;
    maxLength = max(maxLength, size(verdictUnknown));
end

M = zeros(maxLength(1),186);
N = zeros(maxLength(1),186);
j = 1;
for i = 5:190
    disp(int2str(i));
    unknownSize = size(dataUnknown{i});
    falseSize = size(dataFalse{i});
    for l = 1:maxLength(1)
        if l <= unknownSize(1)
            M(l, j) = dataUnknown{i}(l);
        else
            M(l, j) = M(l-1, j);
        end
        if l <= falseSize(1)
            N(l, j) = dataFalse{i}(l);
        else
            N(l, j) = N(l-1, j);
        end
    end
    j = j + 1;
end
idVec = 1:maxLength(1);


% subplot(1,3,1);
% for i = 1:j
%     plot(idVec, (M(:,i)));
%     hold on;
% end
% hold off;
% grid on;
% axis([1 length(idVec)*1.1 -0.1 1.1]);
% xlabel('Iteration');
% ylabel('Probability of Unknown');
% 
% subplot(1,3,2);
% for i = 1:j
%     plot(idVec, (N(:,i)));
%     hold on;
% end
% hold off;
% grid on;
% axis([1 length(idVec)*1.1 -0.1 1.1]);
% xlabel('Iteration');
% ylabel('Probability of False');

% M2 = zeros(floor(maxLength(1)/1000),39);
% for i = 1:39
%     for j = 1:floor(maxLength(1)/1000)
%         M2(j,i) = M(j*1000,i);
%     end
% end
% surf(M2);
% 
% N2 = zeros(floor(maxLength(1)/1000),39);
% for i = 1:39
%     for j = 1:floor(maxLength(1)/1000)
%         N2(j,i) = N(j*1000,i);
%     end
% end
% surf(N2);

%subplot(1,3,3);
figure(1);
index = 5:190;
qx = [0     190   190   0   ];
qy = [1.00  1.00  0.99  0.99];
patch(qx, qy, [1 1 1]*0.8, 'LineStyle', 'None');
hold on;
plot(index,M(maxLength(1),:),'-');
hold on;
plot(index,N(maxLength(1),:),'-.');
hold off;
grid on;
axis([5 190 0.0 1.0])
xlabel('MAX\_NODES');
y = ylabel('Verdict Probability at Termination');
set(y, 'Units', 'Normalized', 'Position', [-0.09, 0.5, 0]);
legend('Terminator','Unknown','False','Location','west', 'Orientation', 'vertical');

%plot(idVec,verdictTrue,idVec,verdictFalse,'--',idVec,verdictUnknown,':');
%grid on;
%axis([1 length(idVec)*1.1 -0.1 1.1])
%xlabel('Iteration');
%y = ylabel('Verdict Probability');
%set(y, 'Units', 'Normalized', 'Position', [-0.09, 0.5, 0]);
%legend('True','False','Unknown','Location','south', 'Orientation', 'horizontal');
%legend('boxoff');


%% Print figures
print('graph', '-dpng', '-r300');
